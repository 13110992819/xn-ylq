/**
 * @Title XN808458.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午4:21:12 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.dto.req.XN615115Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 分页查询汇赚宝Oss
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午4:21:12 
 * @history:
 */
public class XN615115 extends AProcessor {
    private IHzbAO hzbAO = SpringContextHolder.getBean(IHzbAO.class);

    private XN615115Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        Hzb condition = new Hzb();
        condition.setUserId(req.getUserId());
        condition.setTemplateCode(req.getTemplateCode());
        condition.setStatus(req.getStatus());
        condition.setPayCode(req.getPayCode());
        condition.setPayGroup(req.getPayGroup());

        condition.setSystemCode(req.getSystemCode());
        condition.setCompanyCode(req.getCompanyCode());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = IHzbAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = StringValidater.toInteger(req.getStart());
        int limit = StringValidater.toInteger(req.getLimit());
        return hzbAO.queryHzbHoldPage(start, limit, condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615115Req.class);
        StringValidater.validateBlank(req.getStart(), req.getLimit(),
            req.getSystemCode(), req.getCompanyCode());
    }

}
