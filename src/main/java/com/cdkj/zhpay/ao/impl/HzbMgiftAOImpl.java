package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbMgiftAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EHzbMgiftStatus;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年2月20日 下午3:21:50 
 * @history:
 */
@Service
public class HzbMgiftAOImpl implements IHzbMgiftAO {
    private static Logger logger = Logger.getLogger(HzbMgiftAOImpl.class);

    @Autowired
    private IHzbMgiftBO hzbMgiftBO;

    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    // 处理思路：
    // 1、将今天之前的红包状态置换为失效
    // 2、根据有效摇钱树，生成当天红包
    @Override
    @Transactional
    public void doDailyHzbMgift() {
        Date todayStart = DateUtil.getTodayStart();
        Date todayEnd = DateUtil.getTodayEnd();
        logger.info("**** 定时红包扫描开始 " + todayStart + " ****");
        // 将今天之前的红包状态置换为失效
        Date yesterdayEnd = DateUtil.getRelativeDateOfDays(todayEnd, -1);
        hzbMgiftBO.doInvalidHzbMgift(yesterdayEnd);
        // 根据有效摇钱树，生成当天红包
        Hzb hhCondition = new Hzb();
        hhCondition.setStatus(EDiviFlag.EFFECT.getCode());

        List<Hzb> hzblist = hzbBO.queryHzbList(hhCondition);
        if (CollectionUtils.isNotEmpty(hzblist)) {
            for (Hzb hzb : hzblist) {
                hzbMgiftBO.generateHzbMgift(hzb, todayStart);
            }
        }
        logger.info("**** 定时红包扫描结束 " + todayStart + " ****");
    }

    @Override
    public void doSendHzbMgift(String hzbMgiftCode) {
        HzbMgift hzbMgift = hzbMgiftBO.getHzbMgift(hzbMgiftCode);
        if (EHzbMgiftStatus.TO_SEND.getCode().equals(hzbMgift.getStatus())) {
            hzbMgiftBO.doSendHzbMgift(hzbMgift);
        } else if (EHzbMgiftStatus.SENT.getCode().equals(hzbMgift.getStatus())) {// 已发送可再次发送，无需更改状态
        } else {
            throw new BizException("xn0000", "该红包不是待发送或已发送待领取状态，无法发送!");
        }
    }

    @Override
    @Transactional
    public void doReceiveHzbMgift(String userId, String hzbMgiftCode) {
        User user = userBO.getRemoteUser(userId);
        HzbMgift hzbMgift = hzbMgiftBO.getHzbMgift(hzbMgiftCode);
        if (hzbMgift.getOwner().equals(userId)) {
            throw new BizException("xn0000", "自己发出的红包无法自己领取!");
        }
        if (EHzbMgiftStatus.TO_SEND.getCode().equals(hzbMgift.getStatus())
                || EHzbMgiftStatus.SENT.getCode().equals(hzbMgift.getStatus())) {
            // 判断当前人员每天领取次数是否超限
            hzbMgiftBO.checkMaxReceive(userId);
            hzbMgiftBO.doReceiveHzbMgift(hzbMgift, user);
            // 领取红包后的分销规则
            doTransferAmountForReceiveHzbMgift(hzbMgift, user);
        } else {
            throw new BizException("xn0000", "定向红包不处于可领取状态，无法领取!");
        }

    }

    // 领取红包后的分销规则
    private void doTransferAmountForReceiveHzbMgift(HzbMgift hzbMgift, User user) {
        // 汇赚宝主人
        String ownerToBizNote = EBizType.AJ_FSDHB.getValue();
        String ownerFromBizNote = UserUtil.getUserMobile(user.getMobile())
                + ownerToBizNote;
        ECurrency currency = ECurrency
            .getECurrency(hzbMgift.getOwnerCurrency());
        String systemUserId = userBO.getSystemUser(hzbMgift.getSystemCode());
        accountBO.doTransferAmountRemote(systemUserId, hzbMgift.getOwner(),
            currency, hzbMgift.getOwnerAmount(), EBizType.AJ_FSDHB,
            ownerFromBizNote, ownerToBizNote);
        // 领取用户
        String reToBizNote = EBizType.AJ_LQHB.getValue();
        String reFromBizNote = UserUtil.getUserMobile(user.getMobile())
                + reToBizNote;
        currency = ECurrency.getECurrency(hzbMgift.getReceiveCurrency());
        accountBO.doTransferAmountRemote(systemUserId, user.getUserId(),
            currency, hzbMgift.getReceiveAmount(), EBizType.AJ_LQHB,
            reFromBizNote, reToBizNote);

    }

    @Override
    public Paginable<HzbMgift> queryHzbMgiftPage(int start, int limit,
            HzbMgift condition) {
        Paginable<HzbMgift> page = hzbMgiftBO.getPaginable(start, limit,
            condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            doGetUsers(page.getList());
        }
        return page;
    }

    @Override
    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition) {
        List<HzbMgift> result = hzbMgiftBO.queryHzbMgiftList(condition);
        doGetUsers(result);
        return result;
    }

    @Override
    public HzbMgift getHzbMgift(String code) {
        HzbMgift result = hzbMgiftBO.getHzbMgift(code);
        doGetUserDetail(result);
        return result;
    }

    private void doGetUsers(List<HzbMgift> list) {
        for (HzbMgift hzbMgift : list) {
            doGetUserDetail(hzbMgift);
        }
    }

    private void doGetUserDetail(HzbMgift hzbMgift) {
        if (StringUtils.isNotBlank(hzbMgift.getOwner())) {
            hzbMgift.setOwnerUser(userBO.getRemoteUser(hzbMgift.getOwner()));
        }
        if (StringUtils.isNotBlank(hzbMgift.getReceiver())) {
            hzbMgift.setReceiverUser(userBO.getRemoteUser(hzbMgift
                .getReceiver()));
        }
    }
}
