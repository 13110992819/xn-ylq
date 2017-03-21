/**
 * @Title XN615100.java 
 * @Package com.cdkj.zhpay.api.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午7:49:27 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615100Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 新增摇钱树模板
 * @author: xieyj 
 * @since: 2017年3月20日 下午7:49:27 
 * @history:
 */
public class XN615100 extends AProcessor {
    private IHzbTemplateAO hzbTemplateAO = SpringContextHolder
        .getBean(IHzbTemplateAO.class);

    private XN615100Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return hzbTemplateAO.addTemplate(req);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615100Req.class);
        StringValidater.validateBlank(req.getName(), req.getPic(),
            req.getPrice(), req.getCurrency(), req.getUpdater(),
            req.getCompanyCode(), req.getSystemCode());

    }

}
