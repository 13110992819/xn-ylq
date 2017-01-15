package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808317Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 夺宝记录详情查询
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:53:04 
 * @history:
 */
public class XN808317 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808317Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return jewelRecordAO.getJewelRecord(req.getCode(), null);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808317Req.class);
        StringValidater.validateBlank(req.getCode());
    }
}
