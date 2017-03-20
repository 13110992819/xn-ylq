/**
 * @Title XN808457.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午4:19:09 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.dto.req.XN808457Req;
import com.cdkj.zhpay.enums.EHzbHoldStatus;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 周边汇赚宝列表查询
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午4:19:09 
 * @history:
 */
public class XN615117 extends AProcessor {
    private IHzbAO hzbAO = SpringContextHolder
        .getBean(IHzbAO.class);

    private XN808457Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Hzb condition = new Hzb();
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
        return hzbAO.queryDistanceHzbHoldList(condition, req.getUserId(),
            req.getDeviceNo());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808457Req.class);
        StringValidater.validateBlank(req.getLatitude(), req.getLongitude(),
            req.getUserId(), req.getDeviceNo());
    }
}
