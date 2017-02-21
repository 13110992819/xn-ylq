package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IJewelTemplateDAO;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午3:10:26 
 * @history:
 */
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
            data.setCurrentPeriods(0);
            data.setStatus(EJewelTemplateStatus.NEW.getCode());
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
            data.setUpdateDatetime(new Date());
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
    public String refreshStatus(String code, EJewelTemplateStatus eStatus,
            String updater, String remark) {
        String status = null;
        if (StringUtils.isNotBlank(code)) {
            JewelTemplate data = new JewelTemplate();
            data.setCode(code);
            data.setStatus(eStatus.getCode());
            data.setUpdater(updater);
            data.setUpdateDatetime(new Date());
            data.setRemark(remark);
            JewelTemplateDAO.updateStatus(data);
            status = eStatus.getCode();
        }
        return status;
    }

    @Override
    public int refreshPeriods(String code, Integer periods) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            JewelTemplate data = new JewelTemplate();
            data.setCode(code);
            data.setCurrentPeriods(periods);
            count = JewelTemplateDAO.updatePeriods(data);
        }
        return count;
    }
}
