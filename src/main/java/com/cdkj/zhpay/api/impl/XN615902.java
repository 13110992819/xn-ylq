/**
 * @Title XNlh5012.java 
 * @Package com.xnjr.moom.api.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午6:50:47 
 * @version V1.0   
 */
package com.cdkj.zhpay.api.impl;

import com.cdkj.zhpay.ao.ISYSDictAO;
import com.cdkj.zhpay.api.AProcessor;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.core.StringValidater;
import com.cdkj.zhpay.dto.req.XN615902Req;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.exception.ParaException;
import com.cdkj.zhpay.spring.SpringContextHolder;

/** 
 * 修改数据字典
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午6:50:47 
 * @history:
 */
public class XN615902 extends AProcessor {
    private ISYSDictAO sysDictAO = SpringContextHolder
        .getBean(ISYSDictAO.class);

    private XN615902Req req = null;

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doBusiness()
     */
    @Override
    public Object doBusiness() throws BizException {
        sysDictAO.editSYSDict(StringValidater.toLong(req.getId()),
            req.getDvalue(), req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    /** 
     * @see com.cdkj.zhpay.api.IProcessor#doCheck(java.lang.String)
     */
    @Override
    public void doCheck(String inputparams) throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN615902Req.class);
        StringValidater.validateBlank(req.getId(), req.getUpdater(),
            req.getDvalue());
    }

}
