/**
 * @Title XN615104.java 
 * @Package com.cdkj.zhpay.api.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午8:14:22 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615104Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 下架摇钱树模板
 * @author: xieyj 
 * @since: 2017年3月20日 下午8:14:22 
 * @history:
 */
public class XN615104 extends AProcessor {
    private IHzbTemplateAO hzbTemplateAO = SpringContextHolder
        .getBean(IHzbTemplateAO.class);

    private XN615104Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        hzbTemplateAO.putOffTemplate(req.getCode(), req.getUpdater(),
            req.getRemark());
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615104Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());

    }

}
