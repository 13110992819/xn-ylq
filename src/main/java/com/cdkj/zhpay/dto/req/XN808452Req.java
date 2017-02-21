/**
 * @Title XN808452Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午3:05:19 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * 汇赚宝购买
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午3:05:19 
 * @history:
 */
public class XN808452Req {
    // 用户编号
    private String userId;

    // 汇赚宝编号
    private String hzbCode;

    // 支付类型(1 内部划转 2 微信 3 支付宝)
    private String payType;

    // ip
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHzbCode() {
        return hzbCode;
    }

    public void setHzbCode(String hzbCode) {
        this.hzbCode = hzbCode;
    }
}
