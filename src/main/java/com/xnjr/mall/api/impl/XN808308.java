package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808308Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 签收
 * @author: xieyj 
 * @since: 2017年1月13日 下午2:40:28 
 * @history:
 */
public class XN808308 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808308Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        jewelRecordAO.signJewel(req.getCode());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808308Req.class);
        StringValidater.validateBlank(req.getCode());
    }
}
