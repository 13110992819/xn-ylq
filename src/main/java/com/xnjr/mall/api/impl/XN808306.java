package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808306Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 在宝贝已上架，且无人购买时，可下架
 * @author: xieyj 
 * @since: 2017年1月12日 下午3:35:59 
 * @history:
 */
public class XN808306 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN808306Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        jewelAO.putOff(req.getCode(), req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808306Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());
    }

}
