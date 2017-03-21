package com.cdkj.zhpay.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.api.converter.ReqConverter;
import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.dto.req.XN615000Req;
import com.cdkj.zhpay.dto.req.XN615002Req;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: xieyj 
 * @since: 2017年2月26日 下午7:32:11 
 * @history:
 */
@Service
public class JewelTemplateAOImpl implements IJewelTemplateAO {

    @Autowired
    private IJewelTemplateBO jewelTemplateBO;

    @Autowired
    private IJewelAO jewelAO;

    @Override
    public String addJewelTemplate(XN615000Req req) {
        JewelTemplate data = ReqConverter.converter(req);
        return jewelTemplateBO.saveJewelTemplate(data);
    }

    @Override
    public void editJewelTemplate(XN615002Req req) {
        if (!jewelTemplateBO.isJewelTemplateExist(req.getCode())) {
            throw new BizException("xn0000", "宝贝模板不存在");
        }
        JewelTemplate data = ReqConverter.converter(req);
        jewelTemplateBO.refreshJewelTemplate(data);
    }

    @Override
    public void dropJewelTemplate(String code) {
        JewelTemplate jewelTemplate = jewelTemplateBO.getJewelTemplate(code);
        if (!EJewelTemplateStatus.NEW.getCode().equals(
            jewelTemplate.getStatus())) {
            throw new BizException("xn0000", "只有未上架的宝贝模板可以删除");
        }
        jewelTemplateBO.removeJewelTemplate(code);
    }

    @Override
    @Transactional
    public String putOn(String code, String updater, String remark) {
        JewelTemplate jewelTemplate = jewelTemplateBO.getJewelTemplate(code);
        if (!EJewelTemplateStatus.NEW.getCode().equals(
            jewelTemplate.getStatus())
                && !EJewelTemplateStatus.PUTOFF.getCode().equals(
                    jewelTemplate.getStatus())) {
            throw new BizException("xn0000", "只有未上架和已下架的宝贝模板可以上架");
        }
        return jewelTemplateBO.refreshStatus(code, EJewelTemplateStatus.PUTON,
            updater, remark);
    }

    @Override
    @Transactional
    public String putOff(String code, String updater, String remark) {
        JewelTemplate jewelTemplate = jewelTemplateBO.getJewelTemplate(code);
        if (!EJewelTemplateStatus.PUTON.getCode().equals(
            jewelTemplate.getStatus())) {
            throw new BizException("xn0000", "只有已上架的宝贝模板可以上架");
        }
        return jewelTemplateBO.refreshStatus(code, EJewelTemplateStatus.PUTOFF,
            updater, remark);
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

}
