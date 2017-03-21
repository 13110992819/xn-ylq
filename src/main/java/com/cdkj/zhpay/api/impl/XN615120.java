package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbYyAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808460Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 领取奖励
 * @author: xieyj 
 * @since: 2017年1月9日 下午4:22:06 
 * @history:
 */
public class XN615120 extends AProcessor {
    private IHzbYyAO hzbYyAO = SpringContextHolder.getBean(IHzbYyAO.class);

    private XN808460Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return hzbYyAO.doHzbYy(req.getUserId(), req.getHzbCode(),
            req.getDeviceNo());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808460Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getHzbCode(),
            req.getDeviceNo());
    }
}
