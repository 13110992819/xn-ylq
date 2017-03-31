package com.cdkj.zhpay.bo.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.domain.Account;
import com.cdkj.zhpay.dto.req.XN002000Req;
import com.cdkj.zhpay.dto.req.XN002100Req;
import com.cdkj.zhpay.dto.req.XN002500Req;
import com.cdkj.zhpay.dto.req.XN002501Req;
import com.cdkj.zhpay.dto.res.PayBalanceRes;
import com.cdkj.zhpay.dto.res.XN001400Res;
import com.cdkj.zhpay.dto.res.XN002000Res;
import com.cdkj.zhpay.dto.res.XN002500Res;
import com.cdkj.zhpay.dto.res.XN002501Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.http.BizConnecter;
import com.cdkj.zhpay.http.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class AccountBOImpl implements IAccountBO {
    static Logger logger = Logger.getLogger(AccountBOImpl.class);

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    public Account getRemoteAccount(String userId, ECurrency currency) {
        XN002000Req req = new XN002000Req();
        req.setUserId(userId);
        req.setCurrency(currency.getCode());
        String jsonStr = BizConnecter.getBizData("002000",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN002000Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN002000Res>>() {
            }.getType());
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn000000", "用户[" + userId + "]账户不存在");
        }
        XN002000Res res = list.get(0);
        Account account = new Account();
        account.setAccountNumber(res.getAccountNumber());
        account.setUserId(res.getUserId());
        account.setRealName(res.getRealName());
        account.setType(res.getType());
        account.setStatus(res.getStatus());

        account.setCurrency(res.getCurrency());
        account.setAmount(res.getAmount());
        account.setFrozenAmount(res.getFrozenAmount());
        account.setCreateDatetime(res.getCreateDatetime());
        account.setLastOrder(res.getLastOrder());

        account.setSystemCode(res.getSystemCode());
        return account;
    }

    @Override
    public void doTransferAmountRemote(String fromUserId, String toUserId,
            ECurrency currency, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote) {
        if (amount != null && amount != 0) {
            XN002100Req req = new XN002100Req();
            req.setFromUserId(fromUserId);
            req.setToUserId(toUserId);
            req.setCurrency(currency.getCode());
            req.setTransAmount(String.valueOf(amount));
            req.setBizType(bizType.getCode());
            req.setFromBizNote(fromBizNote);
            req.setToBizNote(toBizNote);
            BizConnecter.getBizData("002100", JsonUtils.object2Json(req),
                Object.class);
        }
    }

    @Override
    public XN002500Res doWeiXinAppPayRemote(String fromUserId, String toUserId,
            Long amount, EBizType bizType, String fromBizNote,
            String toBizNote, String payGroup) {
        // 获取微信APP支付信息
        XN002500Req req = new XN002500Req();
        req.setFromUserId(fromUserId);
        req.setToUserId(toUserId);
        req.setBizType(bizType.getCode());
        req.setFromBizNote(fromBizNote);
        req.setToBizNote(toBizNote);
        req.setTransAmount(String.valueOf(amount));
        req.setPayGroup(payGroup);
        XN002500Res res = BizConnecter.getBizData("002500",
            JsonUtil.Object2Json(req), XN002500Res.class);
        return res;
    }

    /**
     * 微信H5支付
     * @param fromUserId 来方用户
     * @param fromOpenId 来方openId
     * @param toUserId 去方用户
     * @param amount 发生金额
     * @param bizType 业务类型
     * @param fromBizNote 来方说明
     * @param toBizNote 去方说明
     * @param payGroup 支付组号
     * @return 
     * @create: 2017年3月31日 下午5:44:32 xieyj
     * @history:
     */
    @Override
    public XN002501Res doWeiXinH5PayRemote(String fromUserId,
            String fromOpenId, String toUserId, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote, String payGroup) {
        // 获取微信APP支付信息
        XN002501Req req = new XN002501Req();
        req.setFromUserId(fromUserId);
        req.setToUserId(toUserId);
        req.setTransAmount(String.valueOf(amount));
        req.setBizType(bizType.getCode());
        req.setFromBizNote(fromBizNote);
        req.setToBizNote(toBizNote);
        req.setPayGroup(payGroup);
        XN002501Res res = BizConnecter.getBizData("002501",
            JsonUtil.Object2Json(req), XN002501Res.class);
        return res;
    }

    @Override
    public void checkBalanceAmount(String userId, Long price) {
        Map<String, String> rateMap = sysConfigBO
            .getConfigsMap(ESystemCode.ZHPAY.getCode());
        // 余额支付业务规则：优先扣贡献值，其次扣分润
        Account gxjlAccount = this.getRemoteAccount(userId, ECurrency.GXJL);
        // 查询用户分润账户
        Account frAccount = this.getRemoteAccount(userId, ECurrency.FRB);
        Double gxjl2cny = Double.valueOf(rateMap.get(SysConstants.GXJL2CNY));
        Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
        Long gxjlCnyAmount = Double.valueOf(gxjlAccount.getAmount() / gxjl2cny)
            .longValue();
        Long frCnyAmount = Double.valueOf(frAccount.getAmount() / fr2cny)
            .longValue();
        // 1、贡献值+分润<价格 余额不足
        if (gxjlCnyAmount + frCnyAmount < price) {
            throw new BizException("xn0000", "余额不足");
        }
    }

    @Override
    public PayBalanceRes doBalancePay(String systemCode,
            XN001400Res fromUserRes, String toUserId, Long price,
            EBizType bizType) {
        String fromUserId = fromUserRes.getUserId();
        Long gxjlPrice = 0L;
        Long frPrice = 0L;
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        // 余额支付业务规则：优先扣贡献值，其次扣分润
        Account gxjlAccount = this.getRemoteAccount(fromUserId, ECurrency.GXJL);
        // 查询用户分润账户
        Account frAccount = this.getRemoteAccount(fromUserId, ECurrency.FRB);
        Double gxjl2cny = Double.valueOf(rateMap.get(SysConstants.GXJL2CNY));
        Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
        Long gxjlCnyAmount = Double.valueOf(gxjlAccount.getAmount() / gxjl2cny)
            .longValue();
        Long frCnyAmount = Double.valueOf(frAccount.getAmount() / fr2cny)
            .longValue();
        // 1、贡献值+分润<价格 余额不足
        if (gxjlCnyAmount + frCnyAmount < price) {
            throw new BizException("xn0000", "余额不足");
        }
        // 2、贡献值=0 直接扣分润
        if (gxjlAccount.getAmount() <= 0L) {
            frPrice = Double.valueOf(price * fr2cny).longValue();
        }
        // 3、0<贡献值<price 先扣贡献值，再扣分润
        if (gxjlCnyAmount > 0L && gxjlCnyAmount < price) {
            // 扣除贡献值
            gxjlPrice = gxjlCnyAmount;
            // 再扣除分润
            frPrice = Double.valueOf((price - gxjlCnyAmount) * fr2cny)
                .longValue();
        }
        // 4、贡献值>=price 直接扣贡献值
        if (gxjlCnyAmount >= price) {
            gxjlPrice = Double.valueOf(price * gxjl2cny).longValue();
        }
        // 扣除贡献值
        doTransferAmountRemote(
            fromUserId,
            toUserId,
            ECurrency.GXJL,
            gxjlPrice,
            bizType,
            UserUtil.getUserMobile(fromUserRes.getMobile())
                    + bizType.getValue(), bizType.getValue());
        // 扣除分润
        doTransferAmountRemote(
            fromUserId,
            toUserId,
            ECurrency.FRB,
            frPrice,
            bizType,
            UserUtil.getUserMobile(fromUserRes.getMobile())
                    + bizType.getValue(), bizType.getValue());
        return new PayBalanceRes(gxjlPrice, frPrice);
    }
}
