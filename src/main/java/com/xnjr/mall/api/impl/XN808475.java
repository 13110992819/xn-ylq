package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IHzbMgiftAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.HzbMgift;
import com.xnjr.mall.dto.req.XN808475Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 分页查询红包
 * @author: xieyj 
 * @since: 2017年2月20日 下午6:26:17 
 * @history:
 */
public class XN808475 extends AProcessor {
    private IHzbMgiftAO hzbMgiftAO = SpringContextHolder
        .getBean(IHzbMgiftAO.class);

    private XN808475Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbMgift condition = new HzbMgift();
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
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808475Req.class);
        StringValidater.validateNumber(req.getStart(), req.getLimit());
    }
}
