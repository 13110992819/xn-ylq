package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.domain.Account;
import com.cdkj.zhpay.dto.req.XN002050Req;
import com.cdkj.zhpay.dto.req.XN002051Req;
import com.cdkj.zhpay.dto.req.XN002100Req;
import com.cdkj.zhpay.dto.req.XN002500Req;
import com.cdkj.zhpay.dto.req.XN002501Req;
import com.cdkj.zhpay.dto.req.XN002510Req;
import com.cdkj.zhpay.dto.res.XN002050Res;
import com.cdkj.zhpay.dto.res.XN002051Res;
import com.cdkj.zhpay.dto.res.XN002500Res;
import com.cdkj.zhpay.dto.res.XN002501Res;
import com.cdkj.zhpay.dto.res.XN002510Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
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
        XN002050Req req = new XN002050Req();
        req.setUserId(userId);
        req.setCurrency(currency.getCode());
        String jsonStr = BizConnecter.getBizData("002050",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN002050Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN002050Res>>() {
            }.getType());
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn000000", "用户[" + userId + "]账户不存在");
        }
        XN002050Res res = list.get(0);
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
    public Double getExchangeRateRemote(ECurrency currency) {
        XN002051Req req = new XN002051Req();
        req.setFromCurrency(ECurrency.CNY.getCode());
        req.setToCurrency(currency.getCode());
        XN002051Res res = BizConnecter.getBizData("002051",
            JsonUtil.Object2Json(req), XN002051Res.class);
        return res.getRate();
    }

    @Override
    public void doTransferAmountRemote(String fromUserId, String toUserId,
            ECurrency currency, Long amount, EBizType bizType,
            String fromBizNote, String toBizNote, String refNo) {
        if (amount != null && amount != 0) {
            XN002100Req req = new XN002100Req();
            req.setFromUserId(fromUserId);
            req.setFromCurrency(currency.getCode());
            req.setToUserId(toUserId);
            req.setToCurrency(currency.getCode());
            req.setTransAmount(String.valueOf(amount));
            req.setBizType(bizType.getCode());
            req.setFromBizNote(fromBizNote);
            req.setToBizNote(toBizNote);
            req.setRefNo(refNo);
            BizConnecter.getBizData("002100", JsonUtils.object2Json(req),
                Object.class);
        }
    }

    @Override
    public XN002500Res doWeiXinPayRemote(String applyUser, String toUser,
            String payGroup, String refNo, EBizType bizType, String bizNote,
            Long amount) {
        // 获取微信APP支付信息
        XN002500Req req = new XN002500Req();
        req.setApplyUser(applyUser);
        req.setToUser(toUser);
        req.setPayGroup(payGroup);
        req.setRefNo(refNo);
        req.setBizType(bizType.getCode());
        req.setBizNote(bizNote);
        req.setAmount(String.valueOf(amount));
        req.setBackUrl(PropertiesUtil.Config.PAY_BACK_URL);
        XN002500Res res = BizConnecter.getBizData("002500",
            JsonUtil.Object2Json(req), XN002500Res.class);
        return res;
    }

    /**
     * @see com.xnjr.mall.bo.IAccountBO#doWeiXinH5PayRemote(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.xnjr.mall.enums.EBizType, java.lang.String, java.lang.Long, java.lang.String)
     */
    @Override
    public XN002501Res doWeiXinH5PayRemote(String applyUser, String openId,
            String toUser, String payGroup, String refNo, EBizType bizType,
            String bizNote, Long amount) {
        if (StringUtils.isBlank(openId)) {
            throw new BizException("xn0000", "请先微信登录再支付");
        }
        // 获取微信APP支付信息
        XN002501Req req = new XN002501Req();
        req.setApplyUser(applyUser);
        req.setOpenId(openId);
        req.setToUser(toUser);
        req.setPayGroup(payGroup);
        req.setRefNo(refNo);
        req.setBizType(bizType.getCode());
        req.setBizNote(bizNote);
        req.setAmount(String.valueOf(amount));
        req.setBackUrl(PropertiesUtil.Config.PAY_BACK_URL);
        XN002501Res res = BizConnecter.getBizData("002501",
            JsonUtil.Object2Json(req), XN002501Res.class);
        return res;
    }

    @Override
    public XN002510Res doAlipayRemote(String applyUser, String toUser,
            String payGroup, String refNo, EBizType bizType, String bizNote,
            Long amount) {
        // 获取支付宝APP支付信息
        XN002510Req req = new XN002510Req();
        req.setApplyUser(applyUser);
        req.setToUser(toUser);
        req.setPayGroup(payGroup);
        req.setRefNo(refNo);
        req.setBizType(bizType.getCode());
        req.setBizNote(bizNote);
        req.setAmount(String.valueOf(amount));
        req.setBackUrl(PropertiesUtil.Config.PAY_BACK_URL);
        XN002510Res res = BizConnecter.getBizData("002510",
            JsonUtil.Object2Json(req), XN002510Res.class);
        return res;
    }
}
