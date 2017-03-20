/**
 * @Title XN808404.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午7:45:30 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IStockAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808404Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 股份返还
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午7:45:30 
 * @history:
 */
public class XN808404 extends AProcessor {

    private IStockAO stockAO = SpringContextHolder.getBean(IStockAO.class);

    private XN808404Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        stockAO.returnStock(req.getUserId());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808404Req.class);
        StringValidater.validateBlank(req.getUserId());
    }

}
