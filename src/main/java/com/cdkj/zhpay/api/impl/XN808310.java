package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.dto.req.XN808310Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 宝贝分页查询
 * @author: xieyj 
 * @since: 2017年1月12日 下午2:54:00 
 * @history:
 */
public class XN808310 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN808310Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Jewel condition = new Jewel();
        condition.setTemplateCode(req.getTemplateCode());
        condition.setCreateDatetime(DateUtil.getFrontDate(req.getDateStart(),
            false));
        condition.setCreateDatetime(DateUtil.getFrontDate(req.getDateEnd(),
            true));
        condition.setStatus(req.getStatus());
        condition.setSystemCode(req.getSystemCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IJewelAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jewelAO.queryJewelPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808310Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit(),
            req.getSystemCode());

    }

}