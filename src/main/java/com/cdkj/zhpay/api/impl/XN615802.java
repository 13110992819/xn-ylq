package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbHoldAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808802Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 统计汇赚宝发一发和摇一摇产生的数据
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:44:58 
 * @history:
 */
public class XN615802 extends AProcessor {
    private IHzbHoldAO hzbHoldAO = SpringContextHolder
        .getBean(IHzbHoldAO.class);

    private XN808802Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return hzbHoldAO
            .doGetHzbTotalData(req.getSystemCode(), req.getUserId());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808802Req.class);
        StringValidater.validateBlank(req.getSystemCode(), req.getUserId());
    }
}
