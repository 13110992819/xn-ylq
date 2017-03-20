/**
 * @Title XN808409.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午9:13:14 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IStockHoldAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615218Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 详情查询股份购买记录
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午9:13:14 
 * @history:
 */
public class XN615218 extends AProcessor {
    private IStockHoldAO stockHoldAO = SpringContextHolder
        .getBean(IStockHoldAO.class);

    private XN615218Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return stockHoldAO.getStockHold(StringValidater.toLong(req.getId()));
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615218Req.class);
        StringValidater.validateBlank(req.getId());
    }

}
