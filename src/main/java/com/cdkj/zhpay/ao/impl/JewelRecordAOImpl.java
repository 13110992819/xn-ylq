package com.cdkj.zhpay.ao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN002500Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EJewelRecordStatus;
import com.cdkj.zhpay.enums.EJewelStatus;
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
    public void paySuccess(String payGroup, String payCode, Long transAmount) {
        JewelRecord jewelRecord = jewelRecordBO
            .getJewelRecordByPayGroup(payGroup);
        if (EJewelRecordStatus.TO_PAY.getCode().equals(jewelRecord.getStatus())) {
            Jewel jewel = jewelBO.getJewel(jewelRecord.getJewelCode());
            // 刷新夺宝纪录状态
            jewelRecordBO.refreshPayStatus(jewelRecord.getCode(),
                EJewelRecordStatus.LOTTERY.getCode(), payCode, "待开奖");
            // 号码落地
            jewelRecordNumberBO.saveJewelRecordNumber(jewelRecord.getCode(),
                jewel, jewelRecord.getTimes());
            // 更新夺宝标的已投资人次
            Integer investNum = jewel.getInvestNum() + jewelRecord.getTimes();
            jewelBO.refreshInvestInfo(jewel, investNum);
            // 是否可以开奖，开奖自动开始下一期
            if (investNum >= jewel.getTotalNum()) {
                jewelAO.doManBiao(jewel.getCode());
                jewelAO.publishNextPeriods(jewel.getTemplateCode());
            }
        } else {
            logger.info("订单号：" + jewelRecord.getCode() + "已支付，重复回调");
        }
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

    @Override
    public boolean buyJewelByYE(String userId, String jewelCode, Integer times,
            String ip) {
        // 入参业务检查
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.RUNNING.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于募集中状态，不能进行购买操作");
        }
        User user = userBO.getRemoteUser(userId);
        jewelRecordBO.checkTimes(user, jewel, times);

        Long amount = jewel.getFromAmount() * times;// 本次购买总金额
        // 购买记录落地
        String jewelRecordCode = jewelRecordBO.saveJewelRecord(userId,
            jewelCode, times, amount, ip, jewel.getCompanyCode(),
            jewel.getSystemCode());
        // 号码落地
        jewelRecordNumberBO
            .saveJewelRecordNumber(jewelRecordCode, jewel, times);
        // 更新夺宝标的已投资人次
        Integer investNum = jewel.getInvestNum() + times;
        jewelBO.refreshInvestInfo(jewel, investNum);
        // 支付购买夺宝的钱
        ECurrency currency = ECurrency.getECurrency(jewel.getFromCurrency());
        String systemUserId = userBO.getSystemUser(jewel.getSystemCode());
        accountBO.doTransferAmountRemote(user.getUserId(), systemUserId,
            currency, amount, EBizType.AJ_DUOBAO,
            EBizType.AJ_DUOBAO.getValue(), EBizType.AJ_DUOBAO.getValue());
        // 是否可以开奖，开奖自动开始下一期
        if (investNum >= jewel.getTotalNum()) {
            return true;
        }
        return false;
    }

    @Override
    public Object buyJewelByWX(String userId, String jewelCode, Integer times,
            String ip) {
        // 入参业务检查
        Jewel jewel = jewelBO.getJewel(jewelCode);
        if (!EJewelStatus.RUNNING.getCode().equals(jewel.getStatus())) {
            throw new BizException("xn0000", "夺宝标的不处于募集中状态，不能进行购买操作");
        }
        User user = userBO.getRemoteUser(userId);
        jewelRecordBO.checkTimes(user, jewel, times);
        if (!ECurrency.CNY.getCode().equals(jewel.getFromCurrency())) {
            throw new BizException("xn0000", "购买币种不是人民币，不能使用微信支付");
        }

        // 生成支付组号
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        // 落地小目标购买记录
        jewelRecordBO.saveJewelRecord(userId, jewel.getCode(), times,
            jewel.getFromAmount() * times, ip, payGroup, jewel.getSystemCode());
        XN002500Res res = accountBO.doWeiXinPayRemote(jewel.getSystemCode(),
            jewel.getCompanyCode(), userId, payGroup, EBizType.AJ_DUOBAO,
            jewel.getFromAmount() * times);
        return res;
    }

    @Override
    public Object buyJewelByZFB(String userId, String jewelCode, Integer times,
            String ip) {
        throw new BizException("xn0000", "暂不支付支付宝支付");

    }
}
