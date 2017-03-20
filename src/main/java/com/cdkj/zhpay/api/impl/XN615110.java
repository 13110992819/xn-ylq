/**
 * @Title XN808452.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午3:03:33 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615110Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 购买摇钱树
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午3:03:33 
 * @history:
 */
public class XN615110 extends AProcessor {
    private IHzbTemplateAO hzbTemplateAO = SpringContextHolder
        .getBean(IHzbTemplateAO.class);

    private XN615110Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public synchronized Object doBusiness() throws BizException {
        return hzbTemplateAO.buyHzb(req.getUserId(), req.getHzbCode(),
            req.getPayType(), req.getIp());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615110Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getHzbCode(),
            req.getPayType());
    }
}