package com.xnjr.mall.dto.req;

/**
 * @author: xieyj 
 * @since: 2016年12月25日 下午3:25:44 
 * @history:
 */
public class XN802518Req {
    // 系统编号(必填)
    private String systemCode;

    // 来方账号(必填)
    private String fromAccountNumber;

    // 兑换资金(必填)
    private String transAmount;

    // 兑换比例
    private String rate;

    // 接收方账户(必填)
    private String toAccountNumber;

    // 业务类型
    private String bizType;

    // 业务说明
    private String bizNote;

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
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
}
