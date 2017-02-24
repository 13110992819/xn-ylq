package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbMgiftAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbHoldBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.domain.HzbHold;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EHzbMgiftStatus;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.enums.ESystemCode;
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
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    // 处理思路：
    // 1、查询昨天即将失效的红包，将状态置换为失效
    // 2、获取已经购买汇赚宝的记录，产生n个红包
    @Override
    @Transactional
    public void doDailyHzbMgift() {
        Date today = DateUtil.getTodayStart();
        logger.info("**** 定时红包扫描开始 " + today + " ****");
        Date yesterday = DateUtil.getRelativeDateOfDays(today, -1);
        HzbMgift condition = new HzbMgift();
        condition.setStatus(EHzbMgiftStatus.TO_INVALID.getCode());
        condition.setCreateDatetime(yesterday);
        List<HzbMgift> list = hzbMgiftBO.queryHzbMgiftList(condition);
        for (HzbMgift hzbMgift : list) {
            hzbMgiftBO.refreshHzbMgiftStatus(hzbMgift.getCode(),
                EHzbMgiftStatus.INVALID);
        }
        // 定时器一天只能跑一次
        HzbMgift hmCondition = new HzbMgift();
        hmCondition.setCreateDatetime(today);
        List<HzbMgift> todayList = hzbMgiftBO.queryHzbMgiftList(condition);
        if (CollectionUtils.isNotEmpty(todayList)) {
            throw new BizException("xn0000", "今天已经发放红包，无法继续发放!");
        }
        // 发放红包
        List<HzbHold> hzbHoldlist = hzbHoldBO.queryHzbHoldList(new HzbHold());
        for (HzbHold hzbHold : hzbHoldlist) {
            hzbMgiftBO.sendHzbMgift(hzbHold.getUserId());
        }
        logger.info("**** 定时红包扫描结束 " + today + " ****");
    }

    @Override
    public void doSendHzbMgift(String userId, String hzbMgiftCode) {
        HzbMgift hzbMgift = hzbMgiftBO.getHzbMgift(hzbMgiftCode);
        if (!hzbMgift.getOwner().equals(userId)) {
            throw new BizException("xn0000", "该红包不属于当前用户");
        }
        if (!EHzbMgiftStatus.TO_SEND.getCode().equals(hzbMgift.getStatus())
                && !EHzbMgiftStatus.SENT.getCode().equals(hzbMgift.getStatus())) {
            throw new BizException("xn0000", "该红包不是待发送或已发送待领取状态，无法发送!");
        }
        hzbMgiftBO.refreshHzbMgiftStatus(hzbMgiftCode, EHzbMgiftStatus.SENT);
    }

    @Override
    @Transactional
    public void doReceiveHzbMgift(String userId, String hzbMgiftCode) {
        HzbMgift hzbMgift = hzbMgiftBO.getHzbMgift(hzbMgiftCode);
        if (!EHzbMgiftStatus.SENT.getCode().equals(hzbMgift.getStatus())) {
            throw new BizException("xn0000", "该红包不是发送状态，无法领取!");
        }
        if (hzbMgift.getOwner().equals(userId)) {
            throw new BizException("xn0000", "自己发出的红包无法自己领取!");
        }
        // 判断当前人员每天领取次数是否超限
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(
            ESystemCode.ZHPAY.getCode(), null);
        Long dayRecevieNumber = Long.valueOf(rateMap
            .get(SysConstants.DAY_RECEVIE_NUMBER));
        HzbMgift hmCondition = new HzbMgift();
        hmCondition.setReceiver(userId);
        hmCondition.setCreateDatetime(DateUtil.getTodayStart());
        long totalCount = hzbMgiftBO.getTotalCount(hmCondition);
        if (totalCount > dayRecevieNumber) {
            throw new BizException("xn0000", "已超过每天最大领取次数，无法领取!");
        }
        hzbMgiftBO.refreshHzbMgiftReciever(hzbMgiftCode, userId);
        // 汇赚宝主人
        accountBO.doTransferAmountByUser(ESystemCode.ZHPAY.getCode(),
            ESysUser.SYS_USER.getCode(), hzbMgift.getOwner(),
            hzbMgift.getOwnerCurrency(), hzbMgift.getOwnerAmount(),
            EBizType.AJ_FSDHB.getCode(), EBizType.AJ_FSDHB.getValue());
        // 领取用户
        accountBO.doTransferAmountByUser(ESystemCode.ZHPAY.getCode(),
            ESysUser.SYS_USER.getCode(), userId, hzbMgift.getReceiveCurrency(),
            hzbMgift.getReceiveAmount(), EBizType.AJ_LQHB.getCode(),
            EBizType.AJ_LQHB.getValue());
    }

    @Override
    public Paginable<HzbMgift> queryHzbMgiftPage(int start, int limit,
            HzbMgift condition) {
        return hzbMgiftBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition) {
        return hzbMgiftBO.queryHzbMgiftList(condition);
    }

    @Override
    public HzbMgift getHzbMgift(String code) {
        return hzbMgiftBO.getHzbMgift(code);
    }
}
