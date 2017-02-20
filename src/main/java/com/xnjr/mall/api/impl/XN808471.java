package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IHzbMgiftAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808471Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 接收红包
 * @author: xieyj 
 * @since: 2017年2月20日 下午6:23:17 
 * @history:
 */
public class XN808471 extends AProcessor {

    private IHzbMgiftAO hzbMgiftAO = SpringContextHolder
        .getBean(IHzbMgiftAO.class);

    private XN808471Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        hzbMgiftAO.doReceiveHzbMgift(req.getUserId(), req.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808471Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getCode());
    }
}
