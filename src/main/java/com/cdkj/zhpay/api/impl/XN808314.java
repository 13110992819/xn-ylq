package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808314Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 我的夺宝号码列表查询
 * @author: asus 
 * @since: 2016年12月21日 下午5:06:23 
 * @history:
 */
public class XN808314 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808314Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return jewelRecordAO.getJewelRecord(req.getRecordCode(),
            req.getUserId());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808314Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getRecordCode());
    }

}
