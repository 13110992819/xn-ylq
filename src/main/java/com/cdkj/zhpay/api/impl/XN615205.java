/**
 * @Title XN808401.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午5:15:26 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IStockAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Stock;
import com.cdkj.zhpay.dto.req.XN615205Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 分页查询股份
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午5:15:26 
 * @history:
 */
public class XN615205 extends AProcessor {
    private IStockAO stockAO = SpringContextHolder.getBean(IStockAO.class);

    private XN615205Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Stock condition = new Stock();
        condition.setStatus(req.getStatus());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IStockAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return stockAO.queryStockPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615205Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit());
    }
}
