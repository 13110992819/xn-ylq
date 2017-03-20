/**
 * @Title XNlh5034.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年6月12日 下午1:36:16 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.ISYSConfigAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN808917Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 根据key获取value值
 * @author: haiqingzheng 
 * @since: 2016年6月12日 下午1:36:16 
 * @history:
 */
public class XN808917 extends AProcessor {
    private ISYSConfigAO sysConfigAO = SpringContextHolder
        .getBean(ISYSConfigAO.class);

    private XN808917Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return sysConfigAO.getSYSConfig(req.getKey(), req.getCompanyCode(),
            req.getSystemCode());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN808917Req.class);
        StringValidater.validateBlank(req.getKey(), req.getCompanyCode(),
            req.getSystemCode());
    }
}
