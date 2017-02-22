package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808303Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 参与夺宝
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:21:36 
 * @history:
 */
public class XN808303 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808303Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Integer times = StringValidater.toInteger(req.getTimes());
        return jewelRecordAO.buyJewel(req.getUserId(), req.getJewelCode(),
            times, req.getPayType(), req.getIp());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808303Req.class);
        StringValidater.validateNumber(req.getTimes());
        StringValidater.validateBlank(req.getUserId(), req.getJewelCode(),
            req.getPayType(), req.getIp());
    }
}
