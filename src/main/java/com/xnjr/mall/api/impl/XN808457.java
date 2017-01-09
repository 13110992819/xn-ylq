/**
 * @Title XN808457.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午4:19:09 
 * @version V1.0   
 */
package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IHzbHoldAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.HzbHold;
import com.xnjr.mall.dto.req.XN808457Req;
import com.xnjr.mall.enums.EHzbHoldStatus;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/** 
 * 周边汇赚宝分页列表查询
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午4:19:09 
 * @history:
 */
public class XN808457 extends AProcessor {
    private IHzbHoldAO hzbHoldAO = SpringContextHolder
        .getBean(IHzbHoldAO.class);

    private XN808457Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbHold condition = new HzbHold();
        condition.setUserLatitude(req.getLatitude());
        condition.setUserLongitude(req.getLongitude());
        condition.setStatus(EHzbHoldStatus.ACTIVATED.getCode());
        // String orderColumn = req.getOrderColumn();
        // if (StringUtils.isBlank(orderColumn)) {
        // orderColumn = IHzbHoldAO.DEFAULT_ORDER_COLUMN;
        // }
        // condition.setOrder(orderColumn, req.getOrderDir());
        // int start = StringValidater.toInteger(req.getStart());
        // int limit = StringValidater.toInteger(req.getLimit());
        return hzbHoldAO.queryDistanceHzbHoldList(condition);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808457Req.class);
        StringValidater.validateBlank(req.getLatitude(), req.getLongitude());
    }
}
