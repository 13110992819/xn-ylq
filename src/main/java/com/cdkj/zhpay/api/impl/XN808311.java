package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.dto.req.XN808311Req;
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
public class XN808311 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN808311Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Jewel condition = new Jewel();
        condition.setStatus(req.getStatus());
        condition.setTemplateCode(req.getTemplateCode());
        condition.setSystemCode(req.getSystemCode());
        condition.setCreateDatetime(DateUtil.getFrontDate(req.getDateStart(),
            false));
        condition.setCreateDatetime(DateUtil.getFrontDate(req.getDateEnd(),
            true));
        return jewelAO.queryJewelList(condition);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN808311Req.class);
        StringValidater.validateBlank(req.getSystemCode());
    }
}
