/**
 * @Title XN808350.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午12:45:01 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.dto.req.XN808350Req;
import com.cdkj.zhpay.dto.res.PKCodeRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 新增宝贝模板
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午12:45:01 
 * @history:
 */
public class XN808350 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private XN808350Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        JewelTemplate data = new JewelTemplate();
        data.setCurrency(req.getCurrency());
        data.setAmount(StringValidater.toLong(req.getAmount()));
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setPrice(StringValidater.toLong(req.getPrice()));
        data.setMaxInvestNum(StringValidater.toInteger(req.getMaxInvestNum()));
        data.setAdvText(req.getAdvText());
        data.setAdvPic(req.getAdvPic());
        data.setUpdater(req.getUpdater());
        data.setSystemCode(req.getSystemCode());
        return new PKCodeRes(jewelTemplateAO.addJewelTemplate(data));
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808350Req.class);
        StringValidater.validateBlank(req.getCurrency(), req.getAmount(),
            req.getTotalNum(), req.getPrice(), req.getMaxInvestNum(),
            req.getAdvText(), req.getAdvPic(), req.getUpdater(),
            req.getSystemCode());
    }

}
