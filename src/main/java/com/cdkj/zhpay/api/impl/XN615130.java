package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbMgiftAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808470Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 发送定向红包
 * @author: xieyj 
 * @since: 2017年2月20日 下午6:23:17 
 * @history:
 */
public class XN615130 extends AProcessor {

    private IHzbMgiftAO hzbMgiftAO = SpringContextHolder
        .getBean(IHzbMgiftAO.class);

    private XN808470Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        hzbMgiftAO.doSendHzbMgift(req.getUserId(), req.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808470Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getCode());
    }
}
