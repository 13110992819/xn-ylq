package com.cdkj.zhpay.bo;

import com.cdkj.zhpay.domain.Account;
import com.cdkj.zhpay.dto.res.XN002500Res;
import com.cdkj.zhpay.dto.res.XN002501Res;
import com.cdkj.zhpay.dto.res.XN002510Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;

/**
 * @author: xieyj 
 * @since: 2017年2月25日 下午1:09:12 
 * @history:
 */
public interface IAccountBO {

    /**
     * 根据用户编号和币种获取账户
     * @param userId
     * @param currency
     * @return 
     * @create: 2017年3月23日 下午12:02:11 myb858
     * @history:
     */
    public Account getRemoteAccount(String userId, ECurrency currency);

    /**
     * 获取虚拟币的价值：1人民币等于多少虚拟币
     * @param currency
     * @return 
     * @create: 2017年4月4日 下午7:38:14 xieyj
     * @history:
     */
    public Double getExchangeRateRemote(ECurrency currency);

    /**
     * 根据用户编号进行账户资金划转
     * @param fromUserId
     * @param toUserId
     * @param currency
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote 
     * @create: 2017年3月23日 下午12:19:42 myb858
     * @history:
     */
    public void doTransferAmountRemote(String fromUserId, String toUserId,
            ECurrency currency, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote);

    /**
     * 微信app支付
     * @param fromUserId
     * @param toUserId
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote
     * @param payGroup
     * @return 
     * @create: 2017年3月23日 下午8:34:24 xieyj
     * @history:
     */
    public XN002500Res doWeiXinAppPayRemote(String fromUserId, String toUserId,
            Long amount, EBizType bizType, String fromBizNote,
            String toBizNote, String payGroup);

    /**
     * 微信h5支付
     * @param fromUserId
     * @param fromOpenId
     * @param toUserId
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote
     * @param payGroup 
     * @create: 2017年3月31日 下午11:18:57 xieyj
     * @history:
     */
    public XN002501Res doWeiXinH5PayRemote(String fromUserId,
            String fromOpenId, String toUserId, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote, String payGroup);

    /**
     * 支付宝支付
     * @param fromUserId
     * @param toUserId
     * @param amount
     * @param bizType
     * @param fromBizNote
     * @param toBizNote
     * @param payGroup
     * @return 
     * @create: 2017年4月1日 上午11:48:35 xieyj
     * @history:
     */
    public XN002510Res doAlipayRemote(String fromUserId, String toUserId,
            Long amount, EBizType bizType, String fromBizNote,
            String toBizNote, String payGroup);
}
