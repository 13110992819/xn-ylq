/**
 * @Title XN808353.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午1:59:12 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615003Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 模板上架
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午1:59:12 
 * @history:
 */
public class XN615003 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN615003Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        String status = jewelTemplateAO.putOn(req.getCode(), req.getUpdater(),
            req.getRemark());
        // 上架即发布宝贝
        if (EJewelTemplateStatus.PUTON.getCode().equals(status)) {
            jewelAO.publishNextPeriods(req.getCode());
        }
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615003Req.class);
        StringValidater.validateBlank(req.getCode(), req.getUpdater());
    }
}
