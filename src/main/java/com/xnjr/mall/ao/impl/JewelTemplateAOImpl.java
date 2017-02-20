package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IJewelTemplateAO;
import com.xnjr.mall.bo.IJewelTemplateBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelTemplate;
import com.xnjr.mall.enums.EJewelTemplateStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class JewelTemplateAOImpl implements IJewelTemplateAO {

    @Autowired
    private IJewelTemplateBO JewelTemplateBO;

    @Override
    public String addJewelTemplate(JewelTemplate data) {
        return JewelTemplateBO.saveJewelTemplate(data);
    }

    @Override
    public int editJewelTemplate(JewelTemplate data) {
        if (!JewelTemplateBO.isJewelTemplateExist(data.getCode())) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return JewelTemplateBO.refreshJewelTemplate(data);
    }

    @Override
    public int dropJewelTemplate(String code) {
        if (!JewelTemplateBO.isJewelTemplateExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return JewelTemplateBO.removeJewelTemplate(code);
    }

    @Override
    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition) {
        return JewelTemplateBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition) {
        return JewelTemplateBO.queryJewelTemplateList(condition);
    }

    @Override
    public JewelTemplate getJewelTemplate(String code) {
        return JewelTemplateBO.getJewelTemplate(code);
    }

    @Override
    public int putOnOff(String code, String updater, String remark) {
        JewelTemplate jewelTemplate = JewelTemplateBO.getJewelTemplate(code);
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
        return JewelTemplateBO.refreshStatus(code, status.getCode(), updater,
            remark);
    }
}
