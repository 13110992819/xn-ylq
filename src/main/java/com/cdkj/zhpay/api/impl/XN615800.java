package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IUserAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615800Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/**
 * 统计辖区的汇赚宝数量
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:44:58 
 * @history:
 */
public class XN615800 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN615800Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return userAO.getParterStatistics(req.getUserId());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615800Req.class);
        StringValidater.validateBlank(req.getUserId());
    }
}
