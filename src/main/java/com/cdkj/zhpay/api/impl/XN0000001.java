package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IAccountAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN0000001Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 待移至account
 * 不同币种间账户兑换审批
 * @author: xieyj 
 * @since: 2017年1月4日 下午9:06:32 
 * @history:
 */
public class XN0000001 extends AProcessor {
    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN0000001Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        accountAO.approveExchangeAmount(req.getSystemCode(), req.getCode(),
            req.getBizType(), req.getApproveResult(), req.getApprover(),
            req.getApproveNote());
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN0000001Req.class);
        StringValidater.validateBlank(req.getSystemCode(), req.getCode(),
            req.getBizType(), req.getApproveResult(), req.getApprover(),
            req.getApproveNote());
    }
}
