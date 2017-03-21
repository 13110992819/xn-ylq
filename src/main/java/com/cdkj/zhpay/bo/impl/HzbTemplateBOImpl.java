package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IHzbTemplateDAO;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.enums.EHzbTemplateStatus;
import com.cdkj.zhpay.exception.BizException;

@Component
public class HzbTemplateBOImpl extends PaginableBOImpl<HzbTemplate> implements
        IHzbTemplateBO {

    @Autowired
    private IHzbTemplateDAO hzbTemplateDAO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public void saveHzbTemplate(HzbTemplate data) {
        if (StringUtils.isNotBlank(data.getCode())) {
            hzbTemplateDAO.insert(data);
        }
    }

    @Override
    public int refreshHzbTemplate(HzbTemplate data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = hzbTemplateDAO.update(data);
        }
        return count;
    }

    @Override
    public List<HzbTemplate> queryHzbTemplateList(HzbTemplate condition) {
        return hzbTemplateDAO.selectList(condition);
    }

    @Override
    public HzbTemplate getHzbTemplate(String code) {
        HzbTemplate data = null;
        if (StringUtils.isNotBlank(code)) {
            HzbTemplate condition = new HzbTemplate();
            condition.setCode(code);
            data = hzbTemplateDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝模板不存在");
            }
        }
        return data;
    }

    @Override
    public int putOnTemplate(String code, String updater, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(updater)) {
            HzbTemplate data = new HzbTemplate();
            data.setCode(code);
            data.setStatus(EHzbTemplateStatus.ON.getCode());
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            count = hzbTemplateDAO.putOnTemplate(data);
        }
        return count;

    }

    @Override
    public int putOffTemplate(String code, String updater, String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(updater)) {
            HzbTemplate data = new HzbTemplate();
            data.setCode(code);
            data.setStatus(EHzbTemplateStatus.OFF.getCode());
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            count = hzbTemplateDAO.putOffTemplate(data);
        }
        return count;
    }
}
