/**
 * @Title XN808407.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午8:59:45 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IStockBackAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.StockBack;
import com.cdkj.zhpay.dto.req.XN808407Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 股份返还记录分页查询
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午8:59:45 
 * @history:
 */
public class XN808407 extends AProcessor {
    private IStockBackAO stockBackAO = SpringContextHolder
        .getBean(IStockBackAO.class);

    private XN808407Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        StockBack condition = new StockBack();
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IStockBackAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return stockBackAO.queryStockBackPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808407Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit());
    }

}
