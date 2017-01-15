package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808304Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 发货
 * @author: xieyj 
 * @since: 2017年1月13日 上午10:33:08 
 * @history:
 */
public class XN808304 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808304Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        jewelRecordAO.sendJewel(req.getCode(), req.getUpdater(),
            req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808304Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());
    }
}
