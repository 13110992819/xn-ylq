/**
 * @Title XN808354.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午2:48:32 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.dto.req.XN615005Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**  
 * 宝贝模板分页查询
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午2:48:32 
 * @history:
 */
public class XN615005 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private XN615005Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        JewelTemplate condition = new JewelTemplate();
        condition.setStatus(req.getStatus());
        condition.setUpdater(req.getUpdater());
        condition.setSystemCode(req.getSystemCode());
        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IJewelTemplateAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return jewelTemplateAO.queryJewelTemplatePage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615005Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit(),
            req.getSystemCode());
    }
}
