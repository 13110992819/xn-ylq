package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IAccountAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.dto.req.XN808500Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 不同账户间兑换
 * @author: xieyj 
 * @since: 2017年1月4日 下午9:06:32 
 * @history:
 */
public class XN808500 extends AProcessor {
    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN808500Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        // accountAO.exchangeAmount(systemCode, userId, type, transAmount,
        // bizType, bizNote);
        return null;
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        // req = JsonUtil.json2Bean(inputparams, XN808459Req.class);
        // StringValidater.validateBlank(req.getId());
    }

}
