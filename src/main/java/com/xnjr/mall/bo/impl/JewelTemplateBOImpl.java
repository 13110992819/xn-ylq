package com.xnjr.mall.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IJewelTemplateBO;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.dao.IJewelTemplateDAO;
import com.xnjr.mall.domain.JewelTemplate;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.exception.BizException;

@Component
public class JewelTemplateBOImpl extends PaginableBOImpl<JewelTemplate>
        implements IJewelTemplateBO {

    @Autowired
    private IJewelTemplateDAO JewelTemplateDAO;

    @Override
    public boolean isJewelTemplateExist(String code) {
        JewelTemplate condition = new JewelTemplate();
        condition.setCode(code);
        if (JewelTemplateDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public String saveJewelTemplate(JewelTemplate data) {
        String code = null;
        if (data != null) {
            code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL_TEMPLETE
                .getCode());
            data.setCode(code);
            data.setUpdateDatetime(new Date());
            JewelTemplateDAO.insert(data);
        }
        return code;
    }

    @Override
    public int removeJewelTemplate(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelTemplate data = new JewelTemplate();
            data.setCode(code);
            count = JewelTemplateDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshJewelTemplate(JewelTemplate data) {
        int count = 0;
        if (StringUtils.isNotBlank(data.getCode())) {
            count = JewelTemplateDAO.update(data);
        }
        return count;
    }

    @Override
    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition) {
        return JewelTemplateDAO.selectList(condition);
    }

    @Override
    public JewelTemplate getJewelTemplate(String code) {
        JewelTemplate data = null;
        if (StringUtils.isNotBlank(code)) {
            JewelTemplate condition = new JewelTemplate();
            condition.setCode(code);
            data = JewelTemplateDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "记录不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshStatus(String code, String status, String updater,
            String remark) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelTemplate data = new JewelTemplate();
            data.setCode(code);
            data.setStatus(status);
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            count = JewelTemplateDAO.updateStatus(data);
        }
        return count;
    }
}
