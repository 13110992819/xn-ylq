package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.ao.IJewelTemplateAO;
import com.xnjr.mall.bo.IJewelTemplateBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelTemplate;
import com.xnjr.mall.enums.EJewelTemplateStatus;
import com.xnjr.mall.exception.BizException;

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
            throw new BizException("xn0000", "记录编号不存在");
        }
        return jewelTemplateBO.refreshJewelTemplate(data);
    }

    @Override
    public int dropJewelTemplate(String code) {
        if (!jewelTemplateBO.isJewelTemplateExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return jewelTemplateBO.removeJewelTemplate(code);
    }

    @Override
    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition) {
        return jewelTemplateBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition) {
        return jewelTemplateBO.queryJewelTemplateList(condition);
    }

    @Override
    public JewelTemplate getJewelTemplate(String code) {
        return jewelTemplateBO.getJewelTemplate(code);
    }

    @Override
    @Transactional
    public int putOnOff(String code, String updater, String remark) {
        JewelTemplate jewelTemplate = jewelTemplateBO.getJewelTemplate(code);
        EJewelTemplateStatus status = null;
        if (EJewelTemplateStatus.NEW.getCode()
            .equals(jewelTemplate.getStatus())
                || EJewelTemplateStatus.PUTOFF.getCode().equals(
                    jewelTemplate.getStatus())) {
            status = EJewelTemplateStatus.PUTON;
            jewelAO.publishNextPeriods(code);
        } else if (EJewelTemplateStatus.PUTON.getCode().equals(
            jewelTemplate.getStatus())) {
            status = EJewelTemplateStatus.PUTOFF;
        }
        return jewelTemplateBO.refreshStatus(code, status.getCode(), updater,
            remark);
    }
}
