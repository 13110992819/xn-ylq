package com.cdkj.zhpay.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.IJewelRecordNumberAO;
import com.cdkj.zhpay.bo.IJewelRecordNumberBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年1月13日 下午3:04:08 
 * @history:
 */
@Service
public class JewelRecordNumberAOImpl implements IJewelRecordNumberAO {
    @Autowired
    private IJewelRecordNumberBO jewelRecordNumberBO;

    @Override
    public Paginable<JewelRecordNumber> queryJewelRecordNumberPage(int start,
            int limit, JewelRecordNumber condition) {
        return jewelRecordNumberBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber condition) {
        return jewelRecordNumberBO.queryJewelRecordNumberList(condition);
    }

    @Override
    public JewelRecordNumber getJewelRecordNumber(Long id) {
        if (!jewelRecordNumberBO.isJewelRecordNumberExist(id)) {
            throw new BizException("xn0000", "号码不存在");
        }
        return jewelRecordNumberBO.getJewelRecordNumber(id);
    }

}