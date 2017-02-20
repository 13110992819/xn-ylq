package com.xnjr.mall.api.impl;

import com.xnjr.mall.ao.IJewelAO;
import com.xnjr.mall.api.AProcessor;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.dto.req.XN808311Req;
import com.xnjr.mall.exception.BizException;
import com.xnjr.mall.exception.ParaException;
import com.xnjr.mall.http.JsonUtils;
import com.xnjr.mall.spring.SpringContextHolder;

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
