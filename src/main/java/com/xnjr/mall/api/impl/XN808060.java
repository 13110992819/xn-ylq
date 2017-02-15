package com.xnjr.mall.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.dto.req.XN808060Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 支付列表订单(正惠钱包)
 * @author: xieyj 
 * @since: 2016年5月23日 上午9:04:12 
 * @history:
 */
public class XN808060 extends AProcessor {

    private IOrderAO orderAO = SpringContextHolder.getBean(IOrderAO.class);

    private XN808060Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return orderAO.toPayMixOrderList(req.getCodeList(), req.getPayType(),
            req.getIp(), req.getApplyUser());
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808060Req.class);
        StringValidater.validateBlank(req.getPayType(), req.getApplyUser());
        if (CollectionUtils.isEmpty(req.getCodeList())) {
            throw new BizException("xn000000", "订单列表不能为空");
        }
    }
}
