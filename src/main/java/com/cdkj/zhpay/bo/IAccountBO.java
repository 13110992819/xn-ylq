package com.cdkj.zhpay.bo;

import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.PayBalanceRes;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.dto.res.XN802503Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.enums.EBizType;

/**
 * @author: xieyj 
 * @since: 2017年2月25日 下午1:09:12 
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
     * 根据用户编号进行账户资金划转
     * @param systemCode
     * @param fromUserId
     * @param toUserId
     * @param currency
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote 
     * @create: 2017年2月25日 下午12:57:18 xieyj
     * @history:
     */
    public void doTransferAmountByUser(String systemCode, String fromUserId,
            String toUserId, String currency, Long amount, String bizType,
            String fromBizNote, String toBizNote);

    /**
     * 检查余额是否足够支付
     * @param systemCode
     * @param fromUserId
     * @param toUserId
     * @param price 
     * @create: 2017年1月11日 上午10:50:05 xieyj
     * @history:
     */
    public void checkBalanceAmount(String systemCode, String userId, Long price);

    /**
     * 余额支付
     * @param systemCode
     * @param fromUserRes
     * @param toUserId
     * @param price
     * @param bizType
     * @return 
     * @create: 2017年2月25日 下午4:35:16 xieyj
     * @history:
     */
    public PayBalanceRes doBalancePay(String systemCode,
            XN805901Res fromUserRes, String toUserId, Long price,
            EBizType bizType);

    /**
     * 分润支付，返回支付分润值
     * @param systemCode
     * @param fromUser
     * @param toUserId
     * @param price
     * @param bizType
     * @return 
     * @create: 2017年3月22日 下午4:21:19 xieyj
     * @history:
     */
    public Long doFRPay(String systemCode, User fromUser, String toUserId,
            Long price, EBizType bizType);

    /**
     * 微信支付
     * @param systemCode
     * @param userId
     * @param payGroup
     * @param bizType
     * @param cnyAmount
     * @return 
     * @create: 2017年2月27日 下午3:52:09 haiqingzheng
     * @history:
     */
    public XN802180Res doWeiXinPay(String systemCode, String userId,
            String payGroup, EBizType bizType, Long cnyAmount);
}
