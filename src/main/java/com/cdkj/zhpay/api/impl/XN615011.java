package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.dto.req.XN615011Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 宝贝列表查询
 * @author: asus 
 * @since: 2016年12月21日 下午4:39:08 
 * @history:
 */
public class XN615011 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN615011Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Jewel condition = new Jewel();
        condition.setStatus(req.getStatus());
        condition.setTemplateCode(req.getTemplateCode());
        condition.setCompanyCode(req.getCompanyCode());
        condition.setSystemCode(req.getSystemCode());
        return jewelAO.queryJewelList(condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615011Req.class);
        StringValidater.validateBlank(req.getSystemCode());
    }
}
