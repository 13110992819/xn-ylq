package com.cdkj.zhpay.ao.impl;

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
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.LuckyNumberGenerator;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EJewelRecordStatus;
import com.cdkj.zhpay.enums.EJewelStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysAccount;
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
        User user = userBO.getRemoteUser(userId);
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.RUNNING.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于募集中状态，不能进行购买操作");
        }
        jewelRecordBO.checkTimes(userId, jewelCode, jewel.getMaxNum(), times);
        // 判断是否大于剩余购买份数
        if (jewel.getTotalNum() - jewel.getInvestNum() < times) {
            throw new BizException("xn0000", "剩余份数不足");
        }
        userBO.doCheckUser(userId);
        // 余额支付(余额支付)
        if (EPayType.YEFR.getCode().equals(payType)) {
            boolean resultByBalance = doBalancePay(user, times, jewel, ip);
            // 是否可以开奖，开奖自动开始下一期
            if (resultByBalance) {
                lotteryJewel(jewel);
                jewelAO.publishNextPeriods(jewel.getTemplateCode());
            }
            result = new BooleanRes(true);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            if (!ECurrency.CNY.getCode().equals(jewel.getFromCurrency())) {
                throw new BizException("xn0000", "购买币种不是人民币，不能使用微信支付");
            }
            result = doWeixinPay(userId, times, jewel, ip);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
        }
        return result;
    }

    // /**
    // * 余额支付
    // * @param userId
    // * @param times
    // * @param jewel
    // * @param ip
    // * @return
    // * @create: 2017年2月22日 下午3:21:45 xieyj
    // * @history:
    // */
    // @Transactional
    // private boolean doBalancePay(User user, Integer times,
    // Jewel jewel, String ip) {
    // String userId = user.getUserId();
    // Long amount = jewel.getFromAmount() * times;
    // // 检验分润和贡献值是否充足
    // accountBO.checkBalanceAmount(jewel.getSystemCode(), userId, amount);
    // String jewelRecordCode = jewelRecordBO.saveJewelRecord(
    // user.getUserId(), jewel.getCode(), times, amount, ip,
    // jewel.getSystemCode());
    // // 分配号码
    // boolean result = distributeNumber(userId, jewel, times, jewelRecordCode);
    // // 扣除余额
    // accountBO.doBalancePay(jewel.getSystemCode(), user,
    // ESysUser.SYS_USER.getCode(), amount, EBizType.AJ_DUOBAO);
    // return result;
    // }

    /**
     * 余额支付
     * @param userId
     * @param times
     * @param jewel
     * @param ip
     * @return 
     * @create: 2017年2月22日 下午3:21:45 xieyj
     * @history:
     */
    @Transactional
    private boolean doBalancePay(User user, Integer times, Jewel jewel,
            String ip) {
        String userId = user.getUserId();
        Long amount = jewel.getFromAmount() * times;
        // 购买记录落地
        String jewelRecordCode = jewelRecordBO.saveJewelRecord(
            user.getUserId(), jewel.getCode(), times, amount, ip,
            jewel.getCompanyCode(), jewel.getSystemCode());
        // 分配号码
        boolean result = distributeNumber(userId, jewel, times, jewelRecordCode);
        // 扣除余额
        ECurrency currency = ECurrency.getECurrency(jewel.getFromCurrency());
        accountBO.doTransferAmount(user.getUserId(),
            ESysAccount.SYS_ACCOUNT.getCode(), currency, amount,
            EBizType.AJ_DUOBAO, EBizType.AJ_DUOBAO.getValue(),
            EBizType.AJ_DUOBAO.getValue());
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
        // 生成支付组号
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        // 落地小目标购买记录
        jewelRecordBO.saveJewelRecord(userId, jewel.getCode(), times,
            jewel.getFromAmount() * times, ip, payGroup, jewel.getSystemCode());
        XN802180Res res = accountBO.doWeiXinPay(jewel.getSystemCode(), userId,
            payGroup, EBizType.AJ_DUOBAO, jewel.getFromAmount() * times);
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
            jewelRecordNumber.setCompanyCode(jewel.getCompanyCode());
            jewelRecordNumber.setSystemCode(jewel.getSystemCode());
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
        User user = userBO.getRemoteUser(userId);
        // 中奖者加上奖金
        String toBizNote = EBizType.AJ_DUOBAO_PRIZE.getValue();
        String fromBizNote = UserUtil.getUserMobile(user.getMobile())
                + toBizNote;
        accountBO.doTransferFcBySystem(jewel.getSystemCode(), userId,
            jewel.getToCurrency(), jewel.getToAmount(),
            EBizType.AJ_DUOBAO_PRIZE.getCode(), fromBizNote, toBizNote);
    }

    @Override
    public void paySuccess(String payGroup, String payCode, Long transAmount) {
        JewelRecord condition = new JewelRecord();
        condition.setPayGroup(payGroup);
        List<JewelRecord> result = jewelRecordBO
            .queryJewelRecordList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        if (!transAmount.equals(jewelRecordBO.getTotalAmount(payGroup))) {
            throw new BizException("XN000000", "金额校验错误，非正常调用");
        }
        JewelRecord jewelRecord = result.get(0);
        if (EJewelRecordStatus.TO_PAY.getCode().equals(jewelRecord.getStatus())) {
            Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
            boolean isLottery = payJewelRecord(jewelRecord, jewel, payCode);
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
    private boolean payJewelRecord(JewelRecord jewelRecord, Jewel jewel,
            String payCode) {
        jewelRecordBO.refreshPayStatus(jewelRecord.getCode(),
            EJewelRecordStatus.LOTTERY.getCode(), payCode, "待开奖");
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
                Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
                jewelRecord.setJewel(jewel);
                User user = userBO.getRemoteUser(jewelRecord.getUserId());
                jewelRecord.setUser(user);
            }
        }
        return page;
    }

    public Paginable<Jewel> queryMyJewelPage(int start, int limit, String userId) {
        JewelRecord condition = new JewelRecord();
        condition.setUserId(userId);
        return jewelRecordBO.queryMyJewelRecordPage(start, limit, condition);
    }

    @Override
    public JewelRecord getJewelRecord(String code) {
        // 夺宝记录
        JewelRecord jewelRecord = jewelRecordBO.getJewelRecord(code);
        // 宝贝
        Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
        jewelRecord.setJewel(jewel);
        // 参与号码
        List<JewelRecordNumber> jewelRecordNumberList = jewelRecordNumberBO
            .queryJewelRecordNumberList(code);
        jewelRecord.setJewelRecordNumberList(jewelRecordNumberList);
        return jewelRecord;
    }
}
