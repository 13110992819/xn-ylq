package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IHzbMgiftDAO;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.enums.EHzbMgiftStatus;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbMgiftBOImpl extends PaginableBOImpl<HzbMgift> implements
        IHzbMgiftBO {

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbMgiftDAO hzbMgiftDAO;

    @Override
    public boolean isHzbMgiftExist(String code) {
        HzbMgift condition = new HzbMgift();
        condition.setCode(code);
        if (hzbMgiftDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void sendHzbMgift(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            // 发放红包
            Map<String, String> rateMap = sysConfigBO.getConfigsMap(
                ESystemCode.ZHPAY.getCode(), null);
            Long dayNumbers = Long
                .valueOf(rateMap.get(SysConstants.DAY_NUMBER));
            String advTitle = rateMap.get(SysConstants.ADV_TITLE);
            String hzbOwnerCurrency = rateMap
                .get(SysConstants.HZB_OWNER_CURRENCY);
            Long hzbOwnerAmount = Long.valueOf(rateMap
                .get(SysConstants.HZB_OWNER_AMOUNT))
                    * SysConstants.AMOUNT_RADIX;
            String hzbReceiveCurrency = rateMap
                .get(SysConstants.HZB_RECEIVE_CURRENCY);
            Long hzbReceiveAmount = Long.valueOf(rateMap
                .get(SysConstants.HZB_RECEIVE_AMOUNT))
                    * SysConstants.AMOUNT_RADIX;
            Date today = DateUtil.getTodayStart();
            for (int i = 0; i < dayNumbers; i++) {
                HzbMgift data = new HzbMgift();
                data.setCode(OrderNoGenerater.generateME("HM"));
                data.setAdvTitle(advTitle);
                data.setOwner(userId);
                data.setOwnerCurrency(hzbOwnerCurrency);
                data.setOwnerAmount(hzbOwnerAmount);
                data.setReceiveAmount(hzbReceiveAmount);
                data.setReceiveCurrency(hzbReceiveCurrency);
                data.setStatus(EHzbMgiftStatus.TO_SEND.getCode());
                data.setCreateDatetime(today);
                hzbMgiftDAO.insert(data);
            }
        }
    }

    @Override
    public int refreshHzbMgiftStatus(String code, EHzbMgiftStatus status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            HzbMgift data = new HzbMgift();
            data.setCode(code);
            data.setStatus(status.getCode());
            count = hzbMgiftDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public int refreshHzbMgiftReciever(String code, String userId) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            HzbMgift data = new HzbMgift();
            data.setCode(code);
            data.setReceiver(userId);
            data.setReceiveDatetime(new Date());
            data.setStatus(EHzbMgiftStatus.RECEIVE.getCode());
            count = hzbMgiftDAO.updateReciever(data);
        }
        return count;
    }

    @Override
    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition) {
        return hzbMgiftDAO.selectList(condition);
    }

    @Override
    public HzbMgift getHzbMgift(String code) {
        HzbMgift data = null;
        if (StringUtils.isNotBlank(code)) {
            HzbMgift condition = new HzbMgift();
            condition.setCode(code);
            data = hzbMgiftDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "定向红包不存在");
            }
        }
        return data;
    }

    @Override
    public List<HzbMgift> queryReceiveHzbMgift(Date startDate, Date endDate,
            String userId) {
        HzbMgift condition = new HzbMgift();
        condition.setCreateDatetimeStart(startDate);
        condition.setCreateDatetimeEnd(endDate);
        condition.setOwner(userId);
        condition.setStatus(EHzbMgiftStatus.RECEIVE.getCode());
        return hzbMgiftDAO.selectList(condition);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IHzbMgiftBO#getReceiveHzbMgiftCount(java.util.Date, java.util.Date, java.lang.String)
     */
    @Override
    public Long getReceiveHzbMgiftCount(Date startDate, Date endDate,
            String userId) {
        HzbMgift condition = new HzbMgift();
        condition.setCreateDatetimeStart(startDate);
        condition.setCreateDatetimeEnd(endDate);
        condition.setOwner(userId);
        condition.setStatus(EHzbMgiftStatus.RECEIVE.getCode());
        return hzbMgiftDAO.selectTotalCount(condition);

    }
}
