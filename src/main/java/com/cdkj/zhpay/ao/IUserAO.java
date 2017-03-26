/**
 * @Title IUserAO.java 
 * @Package com.xnjr.mall.ao 
 * @Description 
 * @author xieyj  
 * @date 2017年1月15日 下午5:27:44 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao;

import com.cdkj.zhpay.dto.res.XN000003Res;

/** 
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:27:44 
 * @history:
 */
public interface IUserAO {

    public XN000003Res getParterStatistics(String userId);

}
