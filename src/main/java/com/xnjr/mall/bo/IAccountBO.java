/**
 * @Title IAccountBO.java 
 * @Package com.ibis.account.bo 
 * @Description 
 * @author miyb  
 * @date 2015-3-15 下午3:15:49 
 * @version V1.0   
 */
package com.xnjr.mall.bo;

import java.util.Map;

import com.xnjr.mall.dto.res.XN802503Res;
import com.xnjr.mall.enums.EBizType;

/** 
 * @author: miyb 
 * @since: 2015-3-15 下午3:15:49 
 * @history:
 */
public interface IAccountBO {

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

    /**
     * 获取用户所有的账户
     * @param systemCode
     * @param userId
     * @return 
     * @create: 2017年1月10日 下午8:32:36 xieyj
     * @history:
     */
    public Map<String, XN802503Res> getAccountsByUser(String systemCode,
            String userId);

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
     * 根据用户编号进行账户资金划转
     * @param systemCode
     * @param fromUserId
     * @param toUserId
     * @param currency
     * @param amount
     * @param bizType
     * @param bizNote 
     * @create: 2017年1月10日 下午2:37:34 xieyj
     * @history:
     */
    public void doTransferAmountByUser(String systemCode, String fromUserId,
            String toUserId, String currency, Long amount, String bizType,
            String bizNote);

    /**
     * @param systemCode
     * @param userId
     * @param currency
     * @param transAmount
     * @param bizType
     * @param bizNote 
     * @create: 2017年1月10日 下午3:19:52 xieyj
     * @history:
     */
    public void doTransferFcBySystem(String systemCode, String userId,
            String currency, Long transAmount, String bizType, String bizNote);

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
     * @param systemCode
     * @param userId
     * @param gwbPrice
     * @param qbbPrice 
     * @create: 2017年1月10日 下午8:24:48 xieyj
     * @history:
     */
    public void checkGwQbBalance(String systemCode, String userId,
            Long gwbPrice, Long qbbPrice);

    /**
     * 购物和钱包币支付
     * @param systemCode
     * @param fromUserId
     * @param toUserId
     * @param gwbPrice
     * @param qbbPrice
     * @param bizType 
     * @create: 2017年1月10日 下午7:58:07 xieyj
     * @history:
     */
    public void doGWBQBBPay(String systemCode, String fromUserId,
            String toUserId, Long gwbPrice, Long qbbPrice, EBizType bizType);

    /**
     * 余额支付
     * @param systemCode
     * @param fromUserId
     * @param toUserId
     * @param price
     * @param bizType 
     * @create: 2017年1月10日 下午5:44:02 xieyj
     * @history:
     */
    public void doBalancePay(String systemCode, String fromUserId,
            String toUserId, Long price, EBizType bizType);

    /**
     * 购物，钱包和余额支付
     * @param systemCode
     * @param fromUserId
     * @param toUserId
     * @param gwPrice
     * @param qbPrice
     * @param cnyPrice
     * @param bizType 
     * @create: 2017年1月10日 下午8:27:59 xieyj
     * @history:
     */
    public void doGwQbAndBalancePay(String systemCode, String fromUserId,
            String toUserId, Long gwPrice, Long qbPrice, Long cnyPrice,
            EBizType bizType);
}
