package com.cdkj.zhpay.api.impl;

import org.apache.commons.collections.CollectionUtils;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615112Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 赠送汇赚宝(oss_正汇)
 * @author: xieyj 
 * @since: 2017年4月24日 下午1:06:05 
 * @history:
 */
public class XN615112 extends AProcessor {
    private IHzbAO hzbAO = SpringContextHolder.getBean(IHzbAO.class);

    private XN615112Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public synchronized Object doBusiness() throws BizException {
        hzbAO.giveHzbOfOss(req.getUserIdList(), req.getHzbTemplateCode());
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615112Req.class);
        if (CollectionUtils.isNotEmpty(req.getUserIdList())) {
            new BizException("000000", "用户列表不能为空");
        }
        StringValidater.validateBlank(req.getHzbTemplateCode());
    }
}
