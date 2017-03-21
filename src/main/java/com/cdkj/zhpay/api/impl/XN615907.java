/**
 * @Title XNlh5015.java 
 * @Package com.xnjr.moom.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午8:14:13 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.ISYSDictAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615907Req;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 详情查询数据字典
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午8:14:13 
 * @history:
 */
public class XN615907 extends AProcessor {
    private ISYSDictAO sysDictAO = SpringContextHolder
        .getBean(ISYSDictAO.class);

    private XN615907Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        return sysDictAO.getSYSDict(StringValidater.toLong(req.getId()));
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615907Req.class);
        StringValidater.validateBlank(req.getId());
    }
}
