/**
 * @Title IUserAO.java 
 * @Package com.xnjr.mall.ao 
 * @Description 
 * @author xieyj  
 * @date 2017年1月15日 下午5:27:44 
 * @version V1.0   
 */
package com.xnjr.mall.ao;

import com.xnjr.mall.dto.res.XN808800Res;

/** 
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:27:44 
 * @history:
 */
public interface IUserAO {

    public XN808800Res getParterStatistics(String userId);

}
