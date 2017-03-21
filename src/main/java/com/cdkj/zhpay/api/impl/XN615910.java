package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.ISYSConfigAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615910Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 修改系统参数
 * @author: xieyj 
 * @since: 2016年11月23日 下午5:54:40 
 * @history:
 */
public class XN615910 extends AProcessor {
    private ISYSConfigAO sysConfigAO = SpringContextHolder
        .getBean(ISYSConfigAO.class);

    private XN615910Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Long id = StringValidater.toLong(req.getId());
        sysConfigAO.editSYSConfig(id, req.getCvalue(), req.getUpdater(),
            req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615910Req.class);
        StringValidater.validateBlank(req.getId(), req.getCvalue(),
            req.getUpdater());
    }

}
