package com.cdkj.zhpay.bo.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.dao.IHzbTemplateDAO;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbTemplateBOImpl extends PaginableBOImpl<HzbTemplate> implements IHzbTemplateBO {

    @Autowired
    private IHzbTemplateDAO hzbTemplateDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public boolean isHzbExist(String code) {
        HzbTemplate condition = new HzbTemplate();
        condition.setCode(code);
        if (hzbTemplateDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int removeHzb(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            HzbTemplate data = new HzbTemplate();
            data.setCode(code);
            count = hzbTemplateDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshHzb(HzbTemplate data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = hzbTemplateDAO.update(data);
        }
        return count;
    }

    @Override
    public List<HzbTemplate> queryHzbList(HzbTemplate condition) {
        List<HzbTemplate> list = hzbTemplateDAO.selectList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            // hzb价格设置为系统参数
            Map<String, String> rateMap = sysConfigBO.getConfigsMap(
                ESystemCode.ZHPAY.getCode(), null);
            for (HzbTemplate data : list) {
                Long hzbPrice = Double
                    .valueOf(
                        (Double.valueOf(rateMap.get(SysConstants.HZB_PRICE)) * SysConstants.AMOUNT_RADIX))
                    .longValue();
                data.setPrice(hzbPrice);
            }
        }
        return list;
    }

    @Override
    public HzbTemplate getHzb(String code) {
        HzbTemplate data = null;
        if (StringUtils.isNotBlank(code)) {
            HzbTemplate condition = new HzbTemplate();
            condition.setCode(code);
            data = hzbTemplateDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝不存在");
            }
            // hzb价格设置为系统参数
            Map<String, String> rateMap = sysConfigBO.getConfigsMap(
                ESystemCode.ZHPAY.getCode(), null);
            Long hzbPrice = Double
                .valueOf(
                    (Double.valueOf(rateMap.get(SysConstants.HZB_PRICE)) * SysConstants.AMOUNT_RADIX))
                .longValue();
            data.setPrice(hzbPrice);
        }
        return data;
    }
}
