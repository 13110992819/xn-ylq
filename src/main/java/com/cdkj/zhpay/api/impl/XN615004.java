/**
 * @Title XN615004.java 
 * @Package com.cdkj.zhpay.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年3月21日 上午10:50:37 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615004Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 模板下架
 * @author: haiqingzheng 
 * @since: 2017年3月21日 上午10:50:37 
 * @history:
 */
public class XN615004 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private XN615004Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        jewelTemplateAO
            .putOff(req.getCode(), req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615004Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());
    }
}
