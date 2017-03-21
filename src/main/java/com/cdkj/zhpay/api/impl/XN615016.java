package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615016Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.http.JsonUtils;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 宝贝查询详情（不含本宝贝的参与记录）
 * @author: asus 
 * @since: 2016年12月21日 下午4:48:41 
 * @history:
 */
public class XN615016 extends AProcessor {
    private IJewelAO jewelAO = SpringContextHolder.getBean(IJewelAO.class);

    private XN615016Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return jewelAO.getJewel(req.getCode());
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtils.json2Bean(inputparams, XN615016Req.class);
        StringValidater.validateBlank(req.getCode());
    }

}
