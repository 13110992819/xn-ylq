package com.cdkj.zhpay.domain;

import java.util.Date;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
* 汇赚宝摇一摇
* @author: xieyj 
* @since: 2017年01月10日 10:50:24
* @history:
*/
public class HzbYy extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 汇赚宝编号
    private String hzbCode;

    // 摇出币种
    private String yyCurrency;

    // 摇出金额
    private Long yyAmount;

    // 摇的人
    private String userId;

    // 设备编号
    private String deviceNo;

    // 生成时间
    private Date createDatetime;

    // 系统编号
    private String systemCode;

    // 公司编号
    private String companyCode;

    // **********db properties********
    private Date createDatetimeStart;

    private Date createDatetimeEnd;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHzbCode() {
        return hzbCode;
    }

    public void setHzbCode(String hzbCode) {
        this.hzbCode = hzbCode;
    }

    public String getYyCurrency() {
        return yyCurrency;
    }

    public void setYyCurrency(String yyCurrency) {
        this.yyCurrency = yyCurrency;
    }

    public Long getYyAmount() {
        return yyAmount;
    }

    public void setYyAmount(Long yyAmount) {
        this.yyAmount = yyAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

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

    public Date getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(Date createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public Date getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }
}
