package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IAccountAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN000002Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 移动至acctount
 * 获取单个币种转人民币金额
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:44:58 
 * @history:
 */
public class XN000002 extends AProcessor {
    private IAccountAO accountAO = SpringContextHolder
        .getBean(IAccountAO.class);

    private XN000002Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return accountAO.getSingleBZByUser(req.getSystemCode(),
            req.getUserId(), req.getCurrency());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN000002Req.class);
        StringValidater.validateBlank(req.getSystemCode(), req.getUserId(),
            req.getCurrency());
    }
}
