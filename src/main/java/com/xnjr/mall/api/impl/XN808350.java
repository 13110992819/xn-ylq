/**
 * @Title XN808350.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午12:45:01 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelTemplateAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.JewelTemplate;
import com.xnjr.mall.dto.req.XN808350Req;
import com.xnjr.mall.dto.res.PKCodeRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

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
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
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
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
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
