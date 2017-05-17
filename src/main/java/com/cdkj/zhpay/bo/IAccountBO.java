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
     * @param refNo 
     * @create: 2017年5月17日 上午10:46:30 xieyj
     * @history:
     */
    public void doTransferAmountRemote(String fromUserId, String toUserId,
            ECurrency currency, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote, String refNo);

    public XN002500Res doWeiXinPayRemote(String applyUser, String toUser,
            String payGroup, String refNo, EBizType bizType, String bizNote,
            Long amount);

    public XN002501Res doWeiXinH5PayRemote(String applyUser, String openId,
            String toUser, String payGroup, String refNo, EBizType bizType,
            String bizNote, Long amount);

    public XN002510Res doAlipayRemote(String applyUser, String toUser,
            String payGroup, String refNo, EBizType bizType, String bizNote,
            Long amount);
}
