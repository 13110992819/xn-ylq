package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IHzbYyAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.dto.req.XN615125Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 奖励分页查询
 * @author: xieyj
 * @since: 2017年2月26日 下午8:25:39 
 * @history:
 */
public class XN615125 extends AProcessor {
    private IHzbYyAO hzbYyAO = SpringContextHolder.getBean(IHzbYyAO.class);

    private XN615125Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbYy condition = new HzbYy();
        condition.setHzbCode(req.getHzbCode());
        condition.setYyCurrency(req.getYyCurrency());
        condition.setUserId(req.getUserId());
        condition.setDeviceNo(req.getDeviceNo());
        condition.setSystemCode(req.getSystemCode());
        condition.setCompanyCode(req.getCompanyCode());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IHzbYyAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return hzbYyAO.queryHzbYyPage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615125Req.class);
        StringValidater.validateBlank(req.getHzbCode(), req.getSystemCode(),
            req.getCompanyCode());
        StringValidater.validateNumber(req.getStart(), req.getLimit());
    }
}
