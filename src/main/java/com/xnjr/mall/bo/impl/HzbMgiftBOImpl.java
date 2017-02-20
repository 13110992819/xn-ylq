package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IHzbMgiftBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IHzbMgiftDAO;
import com.xnjr.mall.domain.HzbMgift;
import com.xnjr.mall.enums.EHzbMgiftStatus;
import com.xnjr.mall.exception.BizException;

@Component
public class HzbMgiftBOImpl extends PaginableBOImpl<HzbMgift> implements
        IHzbMgiftBO {

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
    public String saveHzbMgift(HzbMgift data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generateM("HM");
            data.setCode(code);
            data.setStatus(EHzbMgiftStatus.TO_SEND.getCode());
            hzbMgiftDAO.insert(data);
        }
        return code;
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

}
