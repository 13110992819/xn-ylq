/**
 * @Title XN802180Req.java 
 * @Package com.std.account.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月23日 上午9:25:46 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * 微信APP支付请求接口，返回预付单信息
 * @author: haiqingzheng 
 * @since: 2016年12月23日 上午9:25:46 
 * @history:
 */
public class XN802180Req {
    // 系统编号（必填）
    private String systemCode;

    // 公司编号（必填）
    private String companyCode;

    // 用户编号（必填）
    private String userId;

    // 业务类型（必填）
    private String bizType;

    // 业务备注（必填）
    private String bizNote;

    // 划转资金(必填)
    private String transAmount;

    // 币种(必填)
    private String currency;

    // 支付组号(必填)
    private String payGroup;

    // 终端IP（必填）—— 用户端实际ip
    private String ip;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizNote() {
        return bizNote;
    }

    public void setBizNote(String bizNote) {
        this.bizNote = bizNote;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
