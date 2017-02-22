package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IJewelRecordNumberAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.JewelRecordNumber;
import com.cdkj.zhpay.dto.req.XN808318Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 宝贝号码分页查询
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:53:04 
 * @history:
 */
public class XN808318 extends AProcessor {
    private IJewelRecordNumberAO jewelRecordNumberAO = SpringContextHolder
        .getBean(IJewelRecordNumberAO.class);

    private XN808318Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        JewelRecordNumber condition = new JewelRecordNumber();
        condition.setJewelCode(req.getJewelCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IJewelRecordNumberAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jewelRecordNumberAO.queryJewelRecordNumberPage(start, limit,
            condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808318Req.class);
        StringValidater.validateBlank(req.getJewelCode());
        StringValidater.validateNumber(req.getStart(), req.getLimit());
    }
}
