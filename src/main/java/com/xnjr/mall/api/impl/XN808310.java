package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.dto.req.XN808310Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

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
