/**
 * @Title IAccountAO.java 
 * @Package com.xnjr.mall.ao 
 * @Description 
 * @author xieyj  
 * @date 2017年1月4日 下午9:11:54 
 * @version V1.0   
 */
package com.xnjr.mall.ao;

/** 
 * @author: xieyj 
 * @since: 2017年1月4日 下午9:11:54 
 * @history:
 */
public interface IAccountAO {

    /**
     * 审批兑换记录
     * @param systemCode
     * @param code
     * @param approveResult
     * @param approver
     * @param approveNote 
     * @create: 2017年1月5日 上午11:47:04 xieyj
     * @history:
     */
    void approveExchange(String systemCode, String code, String approveResult,
            String approver, String approveNote);
}
