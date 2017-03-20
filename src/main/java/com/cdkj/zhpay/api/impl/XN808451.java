/**
 * @Title XN808451.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午2:53:20 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.dto.req.XN808451Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 汇赚宝查询
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午2:53:20 
 * @history:
 */
public class XN808451 extends AProcessor {
    private IHzbTemplateAO hzbTemplateAO = SpringContextHolder.getBean(IHzbTemplateAO.class);

    private XN808451Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        HzbTemplate condition = new HzbTemplate();
        condition.setSystemCode(req.getSystemCode());
        return hzbTemplateAO.queryHzbList(condition);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808451Req.class);
        StringValidater.validateBlank(req.getSystemCode());
    }

}
