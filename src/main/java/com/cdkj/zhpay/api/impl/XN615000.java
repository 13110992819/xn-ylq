/**
 * @Title XN808350.java 
 * @Package com.xnjr.mall.api.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午12:45:01 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelTemplateAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615000Req;
import com.cdkj.zhpay.dto.res.PKCodeRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 新增宝贝模板
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午12:45:01 
 * @history:
 */
public class XN615000 extends AProcessor {
    private IJewelTemplateAO jewelTemplateAO = SpringContextHolder
        .getBean(IJewelTemplateAO.class);

    private XN615000Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return new PKCodeRes(jewelTemplateAO.addJewelTemplate(req));
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615000Req.class);
        StringValidater.validateBlank(req.getToAmount(), req.getToCurrency(),
            req.getTotalNum(), req.getMaxNum(), req.getFromAmount(),
            req.getFromCurrency(), req.getSlogan(), req.getAdvPic(),
            req.getUpdater(), req.getCompanyCode(), req.getSystemCode());
    }

}
