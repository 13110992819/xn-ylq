package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IAccountAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808801Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 获取余额
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:44:58 
 * @history:
 */
public class XN808801 extends AProcessor {
    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN808801Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return accountAO.getBalanceByUser(req.getSystemCode(), req.getUserId());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808801Req.class);
        StringValidater.validateBlank(req.getSystemCode(), req.getUserId());
    }
}
