package com.xnjr.mall.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IHzbMgiftAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IHzbHoldBO;
import com.xnjr.mall.bo.IHzbMgiftBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.HzbHold;
import com.xnjr.mall.domain.HzbMgift;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EHzbMgiftStatus;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.enums.ESystemCode;
import com.xnjr.mall.exception.BizException;

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
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(
            ESystemCode.ZHPAY.getCode(), null);
        String advTitle = rateMap.get(SysConstants.ADV_TITLE);
        Long dayNumber = Long.valueOf(rateMap.get(SysConstants.DAY_NUMBER));
        String hzbOwnerCurrency = rateMap.get(SysConstants.HZB_OWNER_CURRENCY);
        Long hzbOwnerAmount = Long.valueOf(rateMap
            .get(SysConstants.HZB_OWNER_AMOUNT)) * SysConstants.AMOUNT_RADIX;
        String hzbReceiveCurrency = rateMap
            .get(SysConstants.HZB_RECEIVE_CURRENCY);
        Long hzbReceiveAmount = Long.valueOf(rateMap
            .get(SysConstants.HZB_RECEIVE_AMOUNT)) * SysConstants.AMOUNT_RADIX;
        List<HzbHold> hzbHoldlist = hzbHoldBO.queryHzbHoldList(new HzbHold());
        for (HzbHold hzbHold : hzbHoldlist) {
            for (int i = 0; i < dayNumber.intValue(); i++) {
                HzbMgift data = new HzbMgift();
                data.setAdvTitle(advTitle);
                data.setOwner(hzbHold.getUserId());
                data.setOwnerCurrency(hzbOwnerCurrency);
                data.setOwnerAmount(hzbOwnerAmount);
                data.setReceiveAmount(hzbReceiveAmount);
                data.setReceiveCurrency(hzbReceiveCurrency);
                data.setCreateDatetime(today);
                hzbMgiftBO.saveHzbMgift(data);
            }
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
