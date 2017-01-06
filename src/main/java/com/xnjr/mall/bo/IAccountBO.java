/**
 * @Title IAccountBO.java 
 * @Package com.ibis.account.bo 
 * @Description 
 * @author miyb  
 * @date 2015-3-15 下午3:15:49 
 * @version V1.0   
 */
package com.xnjr.mall.bo;

import com.xnjr.mall.dto.res.XN802503Res;

/** 
 * @author: miyb 
 * @since: 2015-3-15 下午3:15:49 
 * @history:
 */
public interface IAccountBO {
    /**
     * 账户划转资金
     * @param systemCode
     * @param fromAccountNumber
     * @param toAccountNumber
     * @param amount
     * @param bizType
     * @param bizNote
     * @return 
     * @create: 2016年12月28日 下午2:16:09 xieyj
     * @history:
     */
    public void doTransferAmount(String systemCode, String fromAccountNumber,
            String toAccountNumber, Long amount, String bizType, String bizNote);

    /**
     * 不同币种账户之间划转资金
     * @param systemCode
     * @param fromAccountNumber
     * @param toAccountNumber
     * @param amount
     * @param rate 划转比例
     * @param bizType
     * @param bizNote 
     * @create: 2017年1月6日 下午5:22:31 haiqingzheng
     * @history:
     */
    public void doTransferAmountOnRate(String systemCode,
            String fromAccountNumber, String toAccountNumber, Long amount,
            Double rate, String bizType, String bizNote);

    /**
     * 兑换金额审批
     * @param systemCode
     * @param code
     * @param approveResult
     * @param approver
     * @param approveNote 
     * @create: 2017年1月5日 下午2:16:02 xieyj
     * @history:
     */
    public void doExchangeAmount(String systemCode, String code, String rate,
            String approveResult, String approver, String approveNote);

    /**
     * 根据用户编号和币种获取账户
     * @param systemCode
     * @param userId
     * @param currency
     * @return 
     * @create: 2016年12月28日 下午2:29:43 xieyj
     * @history:
     */
    public XN802503Res getAccountByUserId(String systemCode, String userId,
            String currency);
}
