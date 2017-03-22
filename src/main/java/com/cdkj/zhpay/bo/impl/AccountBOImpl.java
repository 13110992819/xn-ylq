package com.cdkj.zhpay.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.common.JsonUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.req.XN802180Req;
import com.cdkj.zhpay.dto.req.XN802503Req;
import com.cdkj.zhpay.dto.req.XN802530Req;
import com.cdkj.zhpay.dto.res.PayBalanceRes;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.dto.res.XN802503Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
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
    public XN802503Res getAccountByUserId(String systemCode, String userId,
            String currency) {
        Map<String, XN802503Res> map = getAccountsByUser(systemCode, userId);
        XN802503Res result = map.get(currency);
        if (null == result) {
            throw new BizException("xn000000", "用户[" + userId + "]账户不存在");
        }
        return result;
    }

    private Map<String, XN802503Res> getAccountsByUser(String systemCode,
            String userId) {
        Map<String, XN802503Res> resultMap = new HashMap<String, XN802503Res>();
        XN802503Req req = new XN802503Req();
        req.setSystemCode(systemCode);
        req.setUserId(userId);
        String jsonStr = BizConnecter.getBizData("802503",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN802503Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN802503Res>>() {
            }.getType());
        for (XN802503Res xn802503Res : list) {
            resultMap.put(xn802503Res.getCurrency(), xn802503Res);
        }
        return resultMap;
    }

    @Override
    public void doTransferAmountByUser(String systemCode, String fromUserId,
            String toUserId, String currency, Long amount, String bizType,
            String fromBizNote, String toBizNote) {
        if (amount != null && amount != 0) {
            XN802530Req req = new XN802530Req();
            req.setSystemCode(systemCode);
            req.setFromUserId(fromUserId);
            req.setToUserId(toUserId);
            req.setCurrency(currency);
            req.setTransAmount(String.valueOf(amount));
            req.setBizType(bizType);
            req.setFromBizNote(fromBizNote);
            req.setToBizNote(toBizNote);
            BizConnecter.getBizData("802530", JsonUtils.object2Json(req),
                Object.class);
        }
    }

    /** 
     * @see com.cdkj.zhpay.bo.IAccountBO#checkBalanceAmount(java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public void checkBalanceAmount(String systemCode, String userId, Long price) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        // 余额支付业务规则：优先扣贡献值，其次扣分润
        XN802503Res gxjlAccount = this.getAccountByUserId(systemCode, userId,
            ECurrency.GXJL.getCode());
        // 查询用户分润账户
        XN802503Res frAccount = this.getAccountByUserId(systemCode, userId,
            ECurrency.FRB.getCode());
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
            XN805901Res fromUserRes, String toUserId, Long price,
            EBizType bizType) {
        String fromUserId = fromUserRes.getUserId();
        Long gxjlPrice = 0L;
        Long frPrice = 0L;
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        // 余额支付业务规则：优先扣贡献值，其次扣分润
        XN802503Res gxjlAccount = this.getAccountByUserId(systemCode,
            fromUserId, ECurrency.GXJL.getCode());
        // 查询用户分润账户
        XN802503Res frAccount = this.getAccountByUserId(systemCode, fromUserId,
            ECurrency.FRB.getCode());
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
        doTransferAmountByUser(
            systemCode,
            fromUserId,
            toUserId,
            ECurrency.GXJL.getCode(),
            gxjlPrice,
            bizType.getCode(),
            UserUtil.getUserMobile(fromUserRes.getMobile())
                    + bizType.getValue(), bizType.getValue());
        // 扣除分润
        doTransferAmountByUser(
            systemCode,
            fromUserId,
            toUserId,
            ECurrency.FRB.getCode(),
            frPrice,
            bizType.getCode(),
            UserUtil.getUserMobile(fromUserRes.getMobile())
                    + bizType.getValue(), bizType.getValue());
        return new PayBalanceRes(gxjlPrice, frPrice);
    }

    @Override
    public Long doFRPay(String systemCode, User fromUser, String toUserId,
            Long price, EBizType bizType) {
        Long frPrice = 0L;
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        // 查询用户分润账户
        XN802503Res frAccount = this.getAccountByUserId(systemCode,
            fromUser.getUserId(), ECurrency.FRB.getCode());
        Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
        Long frCnyAmount = Double.valueOf(frAccount.getAmount() / fr2cny)
            .longValue();
        // 分润<价格 分润币不足
        if (frCnyAmount < price) {
            throw new BizException("xn0000", "分润币不足");
        }
        frPrice = Double.valueOf(price * fr2cny).longValue();
        String fromBizNote = bizType.getValue();
        String toBizNote = "用户[" + fromUser.getMobile() + "] " + fromBizNote;
        doTransferAmountByUser(systemCode, fromUser.getUserId(), toUserId,
            ECurrency.FRB.getCode(), frPrice, bizType.getCode(), fromBizNote,
            toBizNote);
        return frPrice;
    }

    @Override
    public XN802180Res doWeiXinPay(String systemCode, String userId,
            String payGroup, EBizType bizType, Long cnyAmount) {
        // 获取微信APP支付信息
        XN802180Req req = new XN802180Req();
        req.setSystemCode(systemCode);
        req.setCompanyCode(systemCode);
        req.setUserId(userId);
        req.setPayGroup(payGroup);
        req.setBizType(bizType.getCode());
        req.setBizNote(bizType.getValue());
        req.setCurrency(ECurrency.CNY.getCode());
        req.setTransAmount(String.valueOf(cnyAmount));
        XN802180Res res = BizConnecter.getBizData("802180",
            JsonUtil.Object2Json(req), XN802180Res.class);
        return res;
    }
}
