package com.cdkj.zhpay.ao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.dto.req.XN615000Req;
import com.cdkj.zhpay.dto.req.XN615002Req;
import com.cdkj.zhpay.enums.EGeneratePrefix;
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

    @Override
    public String addJewelTemplate(XN615000Req req) {
        String code = OrderNoGenerater.generateM(EGeneratePrefix.JEWEL_TEMPLETE
            .getCode());
        JewelTemplate data = new JewelTemplate();
        data.setCode(code);
        data.setToAmount(StringValidater.toLong(req.getToAmount()));
        data.setToCurrency(req.getToCurrency());
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setMaxNum(StringValidater.toInteger(req.getMaxNum()));

        data.setFromAmount(StringValidater.toLong(req.getFromAmount()));
        data.setFromCurrency(req.getFromCurrency());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setCurrentPeriods(0);

        data.setStatus(EJewelTemplateStatus.NEW.getCode());
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        data.setCompanyCode(req.getCompanyCode());
        data.setSystemCode(req.getSystemCode());
        jewelTemplateBO.saveJewelTemplate(data);
        return code;
    }

    @Override
    public void editJewelTemplate(XN615002Req req) {
        JewelTemplate data = new JewelTemplate();
        data.setCode(req.getCode());
        data.setToAmount(StringValidater.toLong(req.getToAmount()));
        data.setToCurrency(req.getToCurrency());
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setMaxNum(StringValidater.toInteger(req.getMaxNum()));

        data.setFromAmount(StringValidater.toLong(req.getFromAmount()));
        data.setFromCurrency(req.getFromCurrency());
        data.setSlogan(req.getSlogan());
        data.setAdvPic(req.getAdvPic());
        data.setUpdater(req.getUpdater());

        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        jewelTemplateBO.refreshJewelTemplate(data);
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
            throw new BizException("xn0000", "只有已上架的宝贝模板可以下架");
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
