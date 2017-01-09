/**
 * @Title XN808315.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2017年1月9日 下午8:22:49 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.dto.req.XN808315Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/** 
 * 夺宝进度查询
 * @author: haiqingzheng 
 * @since: 2017年1月9日 下午8:22:49 
 * @history:
 */
public class XN808315 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808315Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        JewelRecord condition = new JewelRecord();
        condition.setJewelCode(req.getCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IOrderAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jewelRecordAO.queryJewelRecordPage(start, limit, condition);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808315Req.class);
        StringValidater.validateBlank(req.getCode(), req.getStart(),
            req.getLimit());
    }

}
