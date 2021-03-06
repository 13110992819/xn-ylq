package com.cdkj.zhpay.dto.req;

/**
 * 购买宝贝
 * @author: xieyj 
 * @since: 2017年1月11日 下午7:30:57 
 * @history:
 */
public class XN615021Req {

    // 用户编号（必填）
    public String userId;

    // 宝贝编号（必填）
    public String jewelCode;

    // 购买次数（必填）
    public String times;

    // 支付类型（必填）
    private String payType;

    // ip 地址（必填）
    private String ip;

    // 交易密码（选填）
    private String tradePwd;

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

}
