/**
 * @Title XN808352.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午1:52:05 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelTemplateAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.JewelTemplate;
import com.xnjr.mall.dto.req.XN808352Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午1:52:05 
 * @history:
 */
public class XN808352 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private XN808352Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        JewelTemplate data = new JewelTemplate();
        data.setCode(req.getCode());
        data.setCurrency(req.getCurrency());
        data.setAmount(StringValidater.toLong(req.getAmount()));
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setPrice(StringValidater.toLong(req.getPrice()));
        data.setMaxInvestNum(StringValidater.toInteger(req.getMaxInvestNum()));
        data.setAdvText(req.getAdvText());
        data.setAdvPic(req.getAdvPic());
        data.setUpdater(req.getUpdater());
        jewelTemplateAO.editJewelTemplate(data);
        return new BooleanRes(true);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808352Req.class);
        StringValidater.validateBlank(req.getCode(), req.getCurrency(),
            req.getAmount(), req.getTotalNum(), req.getPrice(),
            req.getMaxInvestNum(), req.getAdvText(), req.getAdvPic(),
            req.getUpdater());
    }
}
