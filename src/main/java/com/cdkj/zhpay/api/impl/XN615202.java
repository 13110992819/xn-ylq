/**
 * @Title XN808400.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月19日 下午4:29:53 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IStockAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Stock;
import com.cdkj.zhpay.dto.req.XN615202Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 修改股份
 * @author: haiqingzheng 
 * @since: 2016年12月19日 下午4:29:53 
 * @history:
 */
public class XN615202 extends AProcessor {
    private IStockAO stockAO = SpringContextHolder.getBean(IStockAO.class);

    private XN615202Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Stock data = new Stock();
        data.setCode(req.getCode());
        data.setName(req.getName());
        data.setPic(req.getPic());
        data.setDescription(req.getDescription());
        data.setCapital(StringValidater.toInteger(req.getCapital()));
        data.setPrice(StringValidater.toLong(req.getPrice()));
        data.setBackInterval(StringValidater.toInteger(req.getBackInterval()));
        data.setWelfare1(StringValidater.toLong(req.getWelfare1()));
        data.setWelfare2(StringValidater.toLong(req.getWelfare2()));
        stockAO.editStock(data);
        return new BooleanRes(true);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615202Req.class);
        StringValidater.validateBlank(req.getCode(), req.getName(),
            req.getCapital(), req.getPrice(), req.getBackInterval(),
            req.getWelfare1());
    }

}
