package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615027Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 我参与的宝贝分页查询
 * @author: asus 
 * @since: 2016年12月21日 下午4:56:49 
 * @history:
 */
public class XN615027 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN615027Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jewelRecordAO.queryMyJewelPage(start, limit, req.getUserId());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615027Req.class);
        StringValidater.validateBlank(req.getUserId());
        StringValidater.validateNumber(req.getStart(), req.getLimit());
    }

}
