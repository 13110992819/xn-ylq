/**
 * @Title IAccountAO.java 
 * @Package com.xnjr.mall.ao 
 * @Description 
 * @author xieyj  
 * @date 2017年1月4日 下午9:11:54 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao;

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
     * @param bizType
     * @param approveResult
     * @param approver
     * @param approveNote 
     * @create: 2017年1月5日 下午2:26:01 xieyj
     * @history:
     */
    void approveExchangeAmount(String systemCode, String code, String bizType,
            String approveResult, String approver, String approveNote);

    /**
     * 获取余额
     * @param systemCode
     * @param userId
     * @return 
     * @create: 2017年2月15日 下午12:46:36 xieyj
     * @history:
     */
    Long getBalanceByUser(String systemCode, String userId);
}
