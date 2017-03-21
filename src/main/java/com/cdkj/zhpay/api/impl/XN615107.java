/**
 * @Title XN615107.java 
 * @Package com.cdkj.zhpay.api.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午7:58:20 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.ao.ISYSDictAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.dto.req.XN615107Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 列表查询摇钱树模板
 * @author: xieyj 
 * @since: 2017年3月20日 下午7:58:20 
 * @history:
 */
public class XN615107 extends AProcessor {
    private IHzbTemplateAO hzbTemplateAO = SpringContextHolder
        .getBean(IHzbTemplateAO.class);

    private XN615107Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbTemplate condition = new HzbTemplate();
        condition.setName(req.getName());
        condition.setStatus(req.getStatus());
        condition.setUpdater(req.getUpdater());
        condition.setSystemCode(req.getSystemCode());
        condition.setCompanyCode(req.getCompanyCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = ISYSDictAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        return hzbTemplateAO.queryHzbTemplateList(condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615107Req.class);
        StringValidater
            .validateBlank(req.getSystemCode(), req.getCompanyCode());
    }

}
