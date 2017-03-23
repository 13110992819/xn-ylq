package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IHzbMgiftAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.dto.req.XN615135Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 分页查询定向红包
 * @author: xieyj 
 * @since: 2017年2月20日 下午6:26:17 
 * @history:
 */
public class XN615135 extends AProcessor {
    private IHzbMgiftAO hzbMgiftAO = SpringContextHolder
        .getBean(IHzbMgiftAO.class);

    private XN615135Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbMgift condition = new HzbMgift();
        condition.setHzbCode(req.getHzbCode());
        condition.setOwner(req.getOwner());
        condition.setReceiver(req.getReceiver());
        condition.setStatus(req.getStatus());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getCreateDatetimeStart(), false));

        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(
            req.getCreateDatetimeEnd(), true));
        condition.setReceiveDatetimeStart(DateUtil.getFrontDate(
            req.getReceiveDatetimeStart(), false));
        condition.setReceiveDatetimeEnd(DateUtil.getFrontDate(
            req.getReceiveDatetimeEnd(), true));
        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IHzbMgiftAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return hzbMgiftAO.queryHzbMgiftPage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615135Req.class);
        StringValidater.validateNumber(req.getStart(), req.getLimit());
        StringValidater
            .validateBlank(req.getCompanyCode(), req.getSystemCode());
    }
}
