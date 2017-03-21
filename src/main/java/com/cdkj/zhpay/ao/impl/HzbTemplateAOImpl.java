package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.dto.req.XN615100Req;
import com.cdkj.zhpay.dto.req.XN615102Req;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbTemplateStatus;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbTemplateAOImpl implements IHzbTemplateAO {
    @Autowired
    private IHzbTemplateBO hzbTemplateBO;

    @Override
    public String addTemplate(XN615100Req req) {
        HzbTemplate data = new HzbTemplate();
        String code = OrderNoGenerater.generateM(EGeneratePrefix.HZB_TEMPLETE
            .getCode());
        data.setCode(code);
        data.setName(req.getName());
        data.setPic(req.getPic());
        data.setPrice(StringValidater.toLong(req.getPrice()));
        data.setCurrency(req.getCurrency());

        data.setPeriodRockNum(StringValidater.toInteger(req.getPeriodRockNum()));
        data.setTotalRockNum(StringValidater.toInteger(req.getTotalRockNum()));
        data.setBackAmount1(StringValidater.toLong(req.getBackAmount1()));
        data.setBackAmount2(StringValidater.toLong(req.getBackAmount2()));
        data.setBackAmount3(StringValidater.toLong(req.getBackAmount3()));

        data.setStatus(EHzbTemplateStatus.TO_ON.getCode());
        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        data.setSystemCode(req.getSystemCode());
        data.setCompanyCode(req.getCompanyCode());
        hzbTemplateBO.saveHzbTemplate(data);
        return code;
    }

    @Override
    public void editTemplate(XN615102Req req) {
        HzbTemplate data = new HzbTemplate();
        data.setCode(req.getCode());
        data.setName(req.getName());
        data.setPic(req.getPic());
        data.setPrice(StringValidater.toLong(req.getPrice()));
        data.setCurrency(req.getCurrency());

        data.setPeriodRockNum(StringValidater.toInteger(req.getPeriodRockNum()));
        data.setTotalRockNum(StringValidater.toInteger(req.getTotalRockNum()));
        data.setBackAmount1(StringValidater.toLong(req.getBackAmount1()));
        data.setBackAmount2(StringValidater.toLong(req.getBackAmount2()));
        data.setBackAmount3(StringValidater.toLong(req.getBackAmount3()));

        data.setUpdater(req.getUpdater());
        data.setUpdateDatetime(new Date());
        data.setRemark(req.getRemark());
        hzbTemplateBO.refreshHzbTemplate(data);

    }

    @Override
    public void putOnTemplate(String code, String updater, String remark) {
        HzbTemplate data = hzbTemplateBO.getHzbTemplate(code);
        if (!EHzbTemplateStatus.ON.getCode().equals(data.getStatus())) {
            hzbTemplateBO.putOnTemplate(code, updater, remark);
        } else {
            throw new BizException("xn000000", "该模板处于已上架状态，不能重复上架");
        }
    }

    @Override
    public void putOffTemplate(String code, String updater, String remark) {
        HzbTemplate data = hzbTemplateBO.getHzbTemplate(code);
        if (EHzbTemplateStatus.ON.getCode().equals(data.getStatus())) {
            hzbTemplateBO.putOffTemplate(code, updater, remark);
        } else {
            throw new BizException("xn000000", "该模板不处于已上架状态，不能下架");
        }
    }

    @Override
    public Paginable<HzbTemplate> queryHzbTemplatePage(int start, int limit,
            HzbTemplate condition) {
        return hzbTemplateBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<HzbTemplate> queryHzbTemplateList(HzbTemplate condition) {
        return hzbTemplateBO.queryHzbTemplateList(condition);
    }

    @Override
    public HzbTemplate getHzbTemplate(String code) {
        return hzbTemplateBO.getHzbTemplate(code);
    }
}
