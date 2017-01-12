package com.xnjr.mall.api.impl;

import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808307Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;

/**
 * 追加
 * @author: haiqingzheng 
 * @since: 2017年1月10日 下午8:10:26 
 * @history:
 */
public class XN808307 extends AProcessor {
    // private IJewelRecordAO jewelRecordAO = SpringContextHolder
    // .getBean(IJewelRecordAO.class);

    private XN808307Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        // jewelRecordAO.additionalBuy(req.getJewelRecordCode(),
        // StringValidater.toInteger(req.getTimes()));
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808307Req.class);
        StringValidater.validateBlank(req.getJewelRecordCode(), req.getTimes());
    }

}
