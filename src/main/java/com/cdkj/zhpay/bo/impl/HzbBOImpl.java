package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IHzbDAO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbBOImpl extends PaginableBOImpl<Hzb> implements IHzbBO {

    @Autowired
    private IHzbDAO hzbDAO;

    @Override
    public boolean isHzbExist(String code) {
        Hzb condition = new Hzb();
        condition.setCode(code);
        if (hzbDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int removeHzb(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Hzb data = new Hzb();
            data.setCode(code);
            count = hzbDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshHzb(Hzb data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = hzbDAO.update(data);
        }
        return count;
    }

    @Override
    public List<Hzb> queryHzbList(Hzb condition) {
        return hzbDAO.selectList(condition);
    }

    @Override
    public Hzb getHzb(String code) {
        Hzb data = null;
        if (StringUtils.isNotBlank(code)) {
            Hzb condition = new Hzb();
            condition.setCode(code);
            data = hzbDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝不存在");
            }
        }
        return data;
    }
}
