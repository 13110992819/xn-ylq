package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.dto.req.XN808305Req;
import com.xnjr.mall.dto.res.BooleanRes;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 宝贝上架
 * @author: shan 
 * @since: 2016年12月20日 下午3:04:22 
 * @history:
 */
public class XN808305 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN808305Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Jewel data = new Jewel();
        data.setCode(req.getCode());
        data.setPrice1(StringValidater.toLong(req.getPrice1()));
        data.setPrice2(StringValidater.toLong(req.getPrice2()));
        data.setPrice3(StringValidater.toLong(req.getPrice3()));
        data.setTotalNum(StringValidater.toInteger(req.getTotalNum()));
        data.setRaiseDays(StringValidater.toInteger(req.getRaiseDays()));
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        jewelAO.putOn(data);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808305Req.class);
        StringValidater.validateBlank(req.getCode(), req.getPrice1(),
            req.getPrice2(), req.getPrice3(), req.getTotalNum(),
            req.getRaiseDays(), req.getUpdater());
    }

}
