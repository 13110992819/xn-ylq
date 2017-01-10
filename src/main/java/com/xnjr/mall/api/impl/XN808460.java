package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IHzbYyAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808460Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 领取奖励
 * @author: xieyj 
 * @since: 2017年1月9日 下午4:22:06 
 * @history:
 */
public class XN808460 extends AProcessor {
    private IHzbYyAO hzbYyAO = SpringContextHolder.getBean(IHzbYyAO.class);

    private XN808460Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Long hzbHoldId = StringValidater.toLong(req.getHzbHoldId());
        return hzbYyAO.doHzbYy(req.getUserId(), hzbHoldId, req.getDeviceNo());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808460Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getHzbHoldId(),
            req.getDeviceNo());
    }
}
