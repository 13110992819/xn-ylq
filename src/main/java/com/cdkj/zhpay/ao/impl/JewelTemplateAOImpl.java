package com.cdkj.zhpay.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;
import com.cdkj.zhpay.exception.BizException;

@Service
public class JewelTemplateAOImpl implements IJewelTemplateAO {

    @Autowired
    private IJewelTemplateBO jewelTemplateBO;

    @Autowired
    private IJewelAO jewelAO;

    @Override
    public String addJewelTemplate(JewelTemplate data) {
        return jewelTemplateBO.saveJewelTemplate(data);
    }

    @Override
    public int editJewelTemplate(JewelTemplate data) {
        if (!jewelTemplateBO.isJewelTemplateExist(data.getCode())) {
            throw new BizException("xn0000", "宝贝模板不存在");
        }
        return jewelTemplateBO.refreshJewelTemplate(data);
    }

    @Override
    public int dropJewelTemplate(String code) {
        if (!jewelTemplateBO.isJewelTemplateExist(code)) {
            throw new BizException("xn0000", "宝贝模板不存在");
        }
        return jewelTemplateBO.removeJewelTemplate(code);
    }

    @Override
    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition) {
        return jewelTemplateBO.getPaginable(start, limit, condition);
    }

    @Override
    public JewelTemplate getJewelTemplate(String code) {
        return jewelTemplateBO.getJewelTemplate(code);
    }

    @Override
    @Transactional
    public String putOnOff(String code, String updater, String remark) {
        JewelTemplate jewelTemplate = jewelTemplateBO.getJewelTemplate(code);
        EJewelTemplateStatus status = null;
        if (EJewelTemplateStatus.NEW.getCode()
            .equals(jewelTemplate.getStatus())
                || EJewelTemplateStatus.PUTOFF.getCode().equals(
                    jewelTemplate.getStatus())) {
            status = EJewelTemplateStatus.PUTON;
        } else if (EJewelTemplateStatus.PUTON.getCode().equals(
            jewelTemplate.getStatus())) {
            status = EJewelTemplateStatus.PUTOFF;
        }
        return jewelTemplateBO.refreshStatus(code, status, updater, remark);
    }
}
