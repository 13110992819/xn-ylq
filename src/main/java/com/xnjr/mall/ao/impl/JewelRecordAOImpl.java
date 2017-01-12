package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.IJewelRecordNumberBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.core.LuckyNumberGenerator;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.domain.JewelRecordNumber;
import com.xnjr.mall.dto.res.XN805901Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EJewelRecordStatus;
import com.xnjr.mall.enums.EJewelStatus;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.exception.BizException;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:22:11 
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

    @Override
    @Transactional
    public Object buyJewel(String userId, String jewelCode, Integer times,
            String payType, String ip) {
        Object result = null;
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.PUT_ON.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于上架状态，不能进行购买操作");
        }
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        // 夺宝记录落地
        JewelRecord data = new JewelRecord();
        data.setUserId(userId);
        data.setJewelCode(jewelCode);
        data.setTimes(times);
        data.setRemark("已分配夺宝号，待开奖");
        data.setSystemCode(jewel.getSystemCode());
        data.setPayAmount1(jewel.getPrice1() * times);
        data.setPayAmount2(jewel.getPrice2() * times);
        data.setPayAmount3(jewel.getPrice3() * times);
        String jewelRecordCode = null;
        String systemCode = userRes.getSystemCode();
        // 余额支付(余额支付)
        if (EPayType.YEZP.getCode().equals(payType)) {
            data.setStatus(EJewelRecordStatus.LOTTERY.getCode());
            result = jewelRecordBO.saveJewelRecord(data);
            // 分配号码
            distributeNumber(jewel, times, jewelRecordCode);
            // 扣除余额
            accountBO.doGwQbAndBalancePay(systemCode, userId,
                ESysUser.SYS_USER.getCode(), data.getPayAmount2(),
                data.getPayAmount3(), data.getPayAmount1(), EBizType.AJ_DUOBAO);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            data.setStatus(EJewelRecordStatus.TO_PAY.getCode());
            jewelRecordCode = jewelRecordBO.saveJewelRecord(data);
            String bizNote = "宝贝单号：" + jewelRecordCode + "——一元夺宝";
            String body = "正汇钱包—一元夺宝";
            result = accountBO.doWeiXinPay(systemCode, userId, EBizType.AJ_GW,
                bizNote, body, jewel.getPrice1(), ip);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
        }
        return result;
    }

    // @Override
    // @Transactional
    // public void additionalBuy(String jewelRecordCode, Integer times,
    // String payType, String ip) {
    // JewelRecord record = jewelRecordBO.getJewelRecord(jewelRecordCode);
    // Jewel jewel = jewelBO.getJewel(record.getJewelCode());
    // if (!EJewelStatus.PUT_ON.getCode().equals(jewel.getStatus())) {
    // throw new BizException("xn0000", "夺宝标的不处于可夺宝状态，不能进行追加操作");
    // }
    // if (times > jewel.getTotalNum() - jewel.getInvestNum()) {
    // throw new BizException("xn0000", "剩余可参与人次不足");
    // }
    //
    // // 更新夺宝记录参与次数
    // Integer newTimes = record.getTimes() + times;
    // jewelRecordBO.refreshTimes(jewelRecordCode, newTimes);
    // // 余额支付(余额支付)
    // if (EPayType.YEZP.getCode().equals(payType)) {
    // Long payAmount1 = jewel.getPrice1() * times;
    // Long payAmount2 = jewel.getPrice2() * times;
    // Long payAmount3 = jewel.getPrice3() * times;
    // // 分配号码
    // distributeNumber(jewel, times, jewelRecordCode);
    // jewelRecordBO.refreshPayAmount(jewelRecordCode, payAmount1,
    // payAmount2, payAmount3);
    // // 扣除余额
    // accountBO.doGwQbAndBalancePay(record.getSystemCode(),
    // record.getUserId(), ESysUser.SYS_USER.getCode(), payAmount2,
    // payAmount3, payAmount1, EBizType.AJ_DUOBAO);
    // } else if (EPayType.WEIXIN.getCode().equals(payType)) {
    // data.setStatus(EJewelRecordStatus.TO_PAY.getCode());
    // jewelRecordCode = jewelRecordBO.saveJewelRecord(data);
    // String bizNote = "宝贝单号：" + jewelRecordCode + "——一元夺宝";
    // String body = "正汇钱包—一元夺宝";
    // result = accountBO.doWeiXinPay(systemCode, userId, EBizType.AJ_GW,
    // bizNote, body, jewel.getPrice1(), ip);
    // } else if (EPayType.ALIPAY.getCode().equals(payType)) {
    // }
    // return result;
    // }

    /**
     * 分配号码
     * @param jewel
     * @param times
     * @param jewelRecordCode 
     * @create: 2017年1月12日 上午10:31:16 xieyj
     * @history:
     */
    private void distributeNumber(Jewel jewel, Integer times,
            String jewelRecordCode) {
        String jewelCode = jewel.getCode();
        // 查询已有号码列表
        List<String> existNumbers = jewelRecordNumberBO
            .queryExistNumbers(jewelCode);
        // 自动生成夺宝号码
        List<String> numbers = LuckyNumberGenerator.generateLuckyNumbers(
            10000000L, Long.valueOf(jewel.getTotalNum()), existNumbers,
            Long.valueOf(times));
        for (int i = 0; i < numbers.size(); i++) {
            JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
            jewelRecordNumber.setJewelCode(jewelCode);
            jewelRecordNumber.setRecordCode(jewelRecordCode);
            jewelRecordNumber.setNumber(numbers.get(i));
            jewelRecordNumberBO.saveJewelRecordNumber(jewelRecordNumber);
        }
        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setJewelCode(jewelCode);
        Long investNum = jewelRecordNumberBO.getTotalCount(condition);
        // 更新夺宝标的已投资人次及已投资金额
        jewelBO.refreshInvestInfo(jewelCode, investNum.intValue());

        // 如果已投满，产生中奖名单
        if (investNum.intValue() == jewel.getTotalNum()) {
            // 产生中奖号码
            String luckyNumber = LuckyNumberGenerator.getLuckyNumber(10000000L,
                Long.valueOf(jewel.getTotalNum()), 0L);
            // 根据幸运号码招到夺宝记录ID
            JewelRecordNumber condition1 = new JewelRecordNumber();
            condition1.setJewelCode(jewelCode);
            condition1.setNumber(luckyNumber);
            JewelRecordNumber jewelRecordNumber = jewelRecordNumberBO
                .queryJewelRecordNumberList(condition1).get(0);
            JewelRecord jewelRecord = jewelRecordBO
                .getJewelRecord(jewelRecordNumber.getRecordCode());
            String winUserId = jewelRecord.getUserId();
            // 更新此次夺宝所有夺宝记录的状态
            jewelRecordBO.refreshStatus(jewelRecord.getCode(),
                EJewelRecordStatus.WINNING.getCode(), "夺宝号" + luckyNumber
                        + "已中奖");
            jewelRecordBO.refreshLostInfo(jewelRecord.getCode(), jewelCode,
                EJewelRecordStatus.LOST.getCode(), "很遗憾，本次未中奖");
            // 更新夺宝标的中奖人信息
            jewelBO.refreshWinInfo(jewelCode, luckyNumber, winUserId);
        }
    }

    @Override
    public void editJewelRecord(JewelRecord data) {
        if (!jewelRecordBO.isJewelRecordExist(data.getCode())) {
            throw new BizException("xn0000", "不存在该记录");
        }
        if (EJewelRecordStatus.WINNING.getCode().equals(
            jewelRecordBO.getJewelRecord(data.getCode()).getStatus())) {
            jewelRecordBO.refreshStatus(data.getCode(),
                EJewelRecordStatus.DELIVERY.getCode(), data.getRemark());
        } else {
            throw new BizException("xn0000", "该号码未中奖，不可发货");
        }
    }

    @Override
    public void dropJewelRecord(String code) {
        if (!jewelRecordBO.isJewelRecordExist(code)) {
            throw new BizException("xn0000", "不存在该记录");
        }
        jewelRecordBO.removeJewelRecord(code);
    }

    @Override
    @Transactional
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
            // 分配号码
            Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
            distributeNumber(jewel, jewelRecord.getTimes(),
                jewelRecord.getCode());
            jewelRecordBO.refreshPaySuccess(jewelRecord.getCode());
            // 扣除金额(购物币和钱包币)
            accountBO.doGWBQBBPay(jewelRecord.getSystemCode(),
                jewelRecord.getUserId(), ESysUser.SYS_USER.getCode(),
                jewelRecord.getPayAmount2(), jewelRecord.getPayAmount3(),
                EBizType.AJ_DUOBAO);
        } else {
            logger.info("订单号：" + jewelRecord.getCode() + "已支付，重复回调");
        }
    }

    @Override
    public Paginable<JewelRecord> queryJewelRecordPage(int start, int limit,
            JewelRecord condition) {
        Paginable<JewelRecord> page = jewelRecordBO.getPaginable(start, limit,
            condition);
        return page;
    }

    @Override
    public Paginable<JewelRecord> queryMyJewelRecordPage(int start, int limit,
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
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition) {
        return jewelRecordBO.queryJewelRecordList(condition);
    }

    @Override
    public JewelRecord getJewelRecord(String code, String userId) {
        if (!jewelRecordBO.isJewelRecordExist(code)) {
            throw new BizException("xn0000", "不存在该记录");
        }
        JewelRecord jewelRecode = jewelRecordBO.getJewelRecord(code);
        if (!jewelRecode.getUserId().equals(userId)) {
            throw new BizException("xn0000", "您没有这个号码");
        }
        JewelRecordNumber jewelRecordNumber = new JewelRecordNumber();
        jewelRecordNumber.setRecordCode(code);
        List<JewelRecordNumber> jewelRecordNumberList = jewelRecordNumberBO
            .queryJewelRecordNumberList(jewelRecordNumber);
        JewelRecord jewelRecord = jewelRecordBO.getJewelRecord(code);
        jewelRecord.setJewelRecordNumberList(jewelRecordNumberList);
        return jewelRecord;
    }

}
