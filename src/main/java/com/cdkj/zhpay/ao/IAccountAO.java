/**
 * @Title IAccountAO.java 
 * @Package com.xnjr.mall.ao 
 * @Description 
 * @author xieyj  
 * @date 2017年1月4日 下午9:11:54 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao;

import com.cdkj.zhpay.dto.res.XN808803Res;

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

    /**
     * 获取分润/贡献奖励相应的人民币价格
     * @param systemCode
     * @param userId
     * @param currency
     * @return 
     * @create: 2017年2月27日 下午10:20:46 xieyj
     * @history:
     */
    public XN808803Res getSingleBZByUser(String systemCode, String userId,
            String currency);
}
