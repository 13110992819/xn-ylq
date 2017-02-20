/**
 * @Title XN808353.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午1:59:12 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelTemplateAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808353Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/** 
 * 模板上下架
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午1:59:12 
 * @history:
 */
public class XN808353 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private XN808353Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        jewelTemplateAO.putOnOff(req.getCode(), req.getUpdater(),
            req.getRemark());
        return new BooleanRes(true);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808353Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());
    }
}
