package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IJewelBO;
import com.cdkj.zhpay.bo.IJewelRecordBO;
import com.cdkj.zhpay.bo.IJewelRecordNumberBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.ISmsOutBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.core.LuckyNumberGenerator;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EJewelRecordStatus;
import com.cdkj.zhpay.enums.EJewelStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年2月21日 下午12:32:52 
 * @history:
 */
@Service
public class JewelRecordAOImpl implements IJewelRecordAO {
    private static final Logger logger = LoggerFactory
        .getLogger(JewelRecordAOImpl.class);

    @Autowired
    private IJewelRecordBO jewelRecordBO;

    @Autowired
    private IJewelBO jewelBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IJewelRecordNumberBO jewelRecordNumberBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IJewelAO jewelAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public Object buyJewel(String userId, String jewelCode, Integer times,
            String payType, String ip) {
        Object result = null;
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.RUNNING.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于募集中状态，不能进行购买操作");
        }
        jewelRecordBO.checkMaxTimes(userId, jewelCode, times);
        // 判断是否大于剩余购买份数
        if (jewel.getTotalNum() - jewel.getInvestNum() < times) {
            throw new BizException("xn0000", "剩余份数不足");
        }
        userBO.doCheckUser(userId);
        // 余额支付(余额支付)
        if (EPayType.YEZP.getCode().equals(payType)) {
            boolean resultByBalance = doBalancePay(userId, times, jewel);
            // 是否可以开奖，开奖自动开始下一期
            if (resultByBalance) {
                lotteryJewel(jewel);
                jewelAO.publishNextPeriods(jewel.getTemplateCode());
            }
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            result = doWeixinPay(userId, times, jewel, ip);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
        }
        return result;
    }

    /**
     * 余额支付
     * @param systemCode
     * @param userId
     * @param times
     * @param jewel
     * @return 
     * @create: 2017年2月21日 下午6:20:37 xieyj
     * @history:
     */
    @Transactional
    private boolean doBalancePay(String userId, Integer times, Jewel jewel) {
        JewelRecord jewelRecord = new JewelRecord();
        String jewelRecordCode = OrderNoGenerater
            .generateM(EGeneratePrefix.JEWEL_RECORD.getCode());
        jewelRecord.setCode(jewelRecordCode);
        jewelRecord.setUserId(userId);
        jewelRecord.setJewelCode(jewel.getCode());
        jewelRecord.setTimes(times);
        jewelRecord.setRemark("已分配号码，待开奖");
        jewelRecord.setSystemCode(jewel.getSystemCode());
        jewelRecord.setPayAmount(jewel.getPrice() * times);
        // 检验分润和贡献奖励是否充足
        accountBO.checkBalanceAmount(jewel.getSystemCode(), userId,
            jewelRecord.getPayAmount());
        jewelRecord.setStatus(EJewelRecordStatus.LOTTERY.getCode());
        Date payDatetime = new Date();
        jewelRecord.setInvestDatetime(payDatetime);
        jewelRecord.setPayDatetime(payDatetime);
        jewelRecordBO.saveJewelRecord(jewelRecord);
        // 分配号码
        boolean result = distributeNumber(userId, jewel, times, jewelRecordCode);
        // 扣除余额
        accountBO.doBalancePay(jewel.getSystemCode(), userId,
            ESysUser.SYS_USER.getCode(), jewelRecord.getPayAmount(),
            EBizType.AJ_DUOBAO);
        return result;
    }

    /**
     * 微信支付
     * @param systemCode
     * @param userId
     * @param times
     * @param jewel
     * @param ip
     * @return 
     * @create: 2017年2月21日 下午6:30:18 xieyj
     * @history:
     */
    @Transactional
    private XN802180Res doWeixinPay(String userId, Integer times, Jewel jewel,
            String ip) {
        JewelRecord jewelRecord = new JewelRecord();
        String jewelRecordCode = OrderNoGenerater
            .generateM(EGeneratePrefix.JEWEL_RECORD.getCode());
        jewelRecord.setCode(jewelRecordCode);
        jewelRecord.setUserId(userId);
        jewelRecord.setJewelCode(jewel.getCode());
        jewelRecord.setTimes(times);
        jewelRecord.setRemark("已分配号码，待开奖");
        jewelRecord.setSystemCode(jewel.getSystemCode());
        jewelRecord.setPayAmount(jewel.getPrice() * times);
        String bizNote = "宝贝单号：" + jewelRecordCode + "——小目标";
        String body = "正汇钱包—小目标支付";
        XN802180Res res = accountBO.doWeiXinPay(jewel.getSystemCode(), userId,
            EBizType.AJ_DUOBAO, bizNote, body, jewelRecord.getPayAmount(), ip);
        jewelRecord.setStatus(EJewelRecordStatus.TO_PAY.getCode());
        jewelRecord.setPayCode(res.getJourCode());
        jewelRecord.setRemark("小目标待支付");
        jewelRecordBO.saveJewelRecord(jewelRecord);
        return res;
    }

    /**
     * 分配号码:
     * 1、查询已有号码，自动生成号码列表
     * 2、更新宝贝的投资人次
     * @param userId 用户编号
     * @param jewel 宝贝
     * @param times 投资次数
     * @param jewelRecordCode 投资记录编号
     * @create: 2017年2月21日 下午6:36:32 xieyj
     * @history:
     */
    private boolean distributeNumber(String userId, Jewel jewel, Integer times,
            String jewelRecordCode) {
        boolean result = false;
        String jewelCode = jewel.getCode();
        // 查询已有号码列表，自动生成号码
        List<String> existNumbers = jewelRecordNumberBO
            .queryExistNumbers(jewelCode);
        // existNumbers 将包含全部号码
        List<String> numbers = LuckyNumberGenerator.generateLuckyNumbers(
            SysConstants.JEWEL_NUM_RADIX, Long.valueOf(jewel.getTotalNum()),
            existNumbers, Long.valueOf(times));
        for (int i = 0; i < numbers.size(); i++) {
            JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
            jewelRecordNumber.setJewelCode(jewelCode);
            jewelRecordNumber.setRecordCode(jewelRecordCode);
            jewelRecordNumber.setNumber(numbers.get(i));
            jewelRecordNumberBO.saveJewelRecordNumber(jewelRecordNumber);
        }
        Integer investNum = jewel.getInvestNum() + times;
        // 更新夺宝标的已投资人次
        jewelBO.refreshInvestInfo(jewelCode, investNum);
        if (investNum >= jewel.getTotalNum()) {
            result = true;
        }
        return result;
    }

    @Transactional
    private void lotteryJewel(Jewel jewel) {
        String jewelCode = jewel.getCode();
        // 取最后投资五条记录的时间之和
        Long randomA = jewelRecordBO.getLastRecordsTimes(jewelCode);
        // 产生中奖号码
        String luckyNumber = LuckyNumberGenerator.getLuckyNumber(
            SysConstants.JEWEL_NUM_RADIX, Long.valueOf(jewel.getTotalNum()),
            randomA, 0L);
        JewelRecord jewelRecord = jewelRecordBO.getWinJewelRecord(jewelCode,
            luckyNumber);
        // 更新中奖记录的状态
        jewelRecordBO.refreshStatus(jewelRecord.getCode(),
            EJewelRecordStatus.WINNING.getCode(), "号码" + luckyNumber + "已中奖");
        // 中奖用户和中奖记录
        String userId = jewelRecord.getUserId();
        String jewelRecordCode = jewelRecord.getCode();
        // 更新参与记录状态
        jewelRecordBO.refreshLostInfo(jewelRecordCode, jewelCode,
            EJewelRecordStatus.LOST.getCode(), "很遗憾，本次未中奖");
        // 更新宝贝中奖号码
        jewelBO.refreshWinInfo(jewelCode, luckyNumber, userId);
        // 中奖者加上奖金
        accountBO.doTransferAmountByUser(jewel.getSystemCode(),
            ESysUser.SYS_USER.getCode(), userId, jewel.getCurrency(),
            jewel.getAmount(), EBizType.AJ_XMB.getCode(), "小目标获得奖励");
    }

    @Override
    public void paySuccess(String payCode) {
        JewelRecord condition = new JewelRecord();
        condition.setPayCode(payCode);
        List<JewelRecord> result = jewelRecordBO
            .queryJewelRecordList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        JewelRecord jewelRecord = result.get(0);
        if (EJewelRecordStatus.TO_PAY.getCode().equals(jewelRecord.getStatus())) {
            Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
            boolean isLottery = payJewelRecord(jewelRecord, jewel);
            // 是否可以开奖，开奖自动开始下一期
            if (isLottery) {
                lotteryJewel(jewel);
                jewelAO.publishNextPeriods(jewel.getTemplateCode());
            }
        } else {
            logger.info("订单号：" + jewelRecord.getCode() + "已支付，重复回调");
        }
    }

    @Transactional
    private boolean payJewelRecord(JewelRecord jewelRecord, Jewel jewel) {
        Date payDatetime = new Date();
        jewelRecordBO.refreshPaySuccess(jewelRecord.getCode(),
            EJewelRecordStatus.LOTTERY.getCode(), payDatetime, "待开奖");
        // 分配号码
        return distributeNumber(jewelRecord.getUserId(), jewel,
            jewelRecord.getTimes(), jewelRecord.getCode());
    }

    @Override
    public Paginable<JewelRecord> queryJewelRecordPage(int start, int limit,
            JewelRecord condition) {
        Paginable<JewelRecord> page = jewelRecordBO.getPaginable(start, limit,
            condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (JewelRecord jewelRecord : page.getList()) {
                JewelRecordNumber imCondition = new JewelRecordNumber();
                imCondition.setRecordCode(jewelRecord.getCode());
                List<JewelRecordNumber> recordNumberList = jewelRecordNumberBO
                    .queryJewelRecordNumberList(imCondition);
                jewelRecord.setJewelRecordNumberList(recordNumberList);
                Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
                jewelRecord.setJewel(jewel);
            }
        }
        return page;
    }

    @Override
    public Paginable<JewelRecord> queryMyJewelRecordPage(int start, int limit,
            JewelRecord condition) {
        return jewelRecordBO.queryMyJewelRecordPage(start, limit, condition);

    }

    @Override
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition) {
        List<JewelRecord> list = jewelRecordBO.queryJewelRecordList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (JewelRecord jewelRecord : list) {
                JewelRecordNumber imCondition = new JewelRecordNumber();
                imCondition.setRecordCode(jewelRecord.getCode());
                List<JewelRecordNumber> recordNumberList = jewelRecordNumberBO
                    .queryJewelRecordNumberList(imCondition);
                jewelRecord.setJewelRecordNumberList(recordNumberList);
                Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
                jewelRecord.setJewel(jewel);
            }
        }
        return list;
    }

    @Override
    public JewelRecord getJewelRecord(String code) {
        // 夺宝记录
        JewelRecord jewelRecord = jewelRecordBO.getJewelRecord(code);
        // 宝贝
        Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
        // 参与号码
        List<JewelRecordNumber> jewelRecordNumberList = jewelRecordNumberBO
            .queryJewelRecordNumberList(code);
        jewelRecord.setJewel(jewel);
        jewelRecord.setJewelRecordNumberList(jewelRecordNumberList);
        return jewelRecord;
    }
}
