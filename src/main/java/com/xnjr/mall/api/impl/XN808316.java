package com.xnjr.mall.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.dto.req.XN808316Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

/**
 * 我中奖的夺宝记录分页查询
 * @author: xieyj 
 * @since: 2017年1月11日 下午5:53:04 
 * @history:
 */
public class XN808316 extends AProcessor {
    private IJewelRecordAO jewelRecordAO = SpringContextHolder
        .getBean(IJewelRecordAO.class);

    private XN808316Req req = null;

    /** 
     * @see com.xnjr.mall.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        JewelRecord condition = new JewelRecord();
        condition.setUserId(req.getUserId());
        condition.setSystemCode(req.getSystemCode());
        condition.setStatus("win"); // 中奖状态 win = 2456
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IJewelRecordAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jewelRecordAO.queryJewelRecordPage(start, limit, condition);
    }

    /** 
     * @see com.xnjr.mall.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808316Req.class);
        StringValidater.validateBlank(req.getUserId(), req.getSystemCode());
        StringValidater.validateNumber(req.getStart(), req.getLimit());
    }
}
