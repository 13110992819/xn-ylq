package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615119Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 我的摇钱树统计数据
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午4:11:19 
 * @history:
 */
public class XN615119 extends AProcessor {
    private IHzbAO hzbAO = SpringContextHolder.getBean(IHzbAO.class);

    private XN615119Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return hzbAO.doGetHzbTotalData(req.getUserId());
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615119Req.class);
        StringValidater.validateBlank(req.getUserId());
    }
}
