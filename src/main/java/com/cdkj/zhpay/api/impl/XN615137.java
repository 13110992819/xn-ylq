package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbMgiftAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.dto.req.XN615137Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 列表查询定向红包
 * @author: xieyj 
 * @since: 2017年2月20日 下午6:26:17 
 * @history:
 */
public class XN615137 extends AProcessor {
    private IHzbMgiftAO hzbMgiftAO = SpringContextHolder
        .getBean(IHzbMgiftAO.class);

    private XN615137Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbMgift condition = new HzbMgift();
        condition.setHzbCode(req.getHzbCode());
        condition.setOwner(req.getOwner());
        condition.setReceiver(req.getReceiver());
        condition.setStatus(req.getStatus());
        condition.setCreateDatetimeStart(DateUtil.getFrontDate(
            req.getCreateDatetimeStart(), false));

        condition.setCreateDatetimeEnd(DateUtil.getFrontDate(
            req.getCreateDatetimeEnd(), true));
        condition.setReceiveDatetimeStart(DateUtil.getFrontDate(
            req.getReceiveDatetimeStart(), false));
        condition.setReceiveDatetimeEnd(DateUtil.getFrontDate(
            req.getReceiveDatetimeEnd(), true));
        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        return hzbMgiftAO.queryHzbMgiftList(condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615137Req.class);
        StringValidater
            .validateBlank(req.getCompanyCode(), req.getSystemCode());
    }
}
