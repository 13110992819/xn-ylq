package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615022Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 夺宝记录详情查询
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:53:04 
 * @history:
 */
public class XN615022 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN615022Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return jewelRecordAO.getJewelRecord(req.getCode());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615022Req.class);
        StringValidater.validateBlank(req.getCode());
    }
}
