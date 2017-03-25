package com.cdkj.zhpay.domain;

import java.util.Date;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
* 定向红包
* @author: xieyj 
* @since: 2017年02月20日 13:17:06
* @history:
*/
public class HzbMgift extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 汇赚宝编号
    private String hzbCode;

    // 标语
    private String slogan;

    // 树主人
    private String owner;

    // 树主人币种(红包形成时就确定)
    private String ownerCurrency;

    // 树主人金额(红包形成时就确定)
    private Long ownerAmount;

    // 接收人币种(红包形成时就确定)
    private String receiveCurrency;

    // 接收人金额(红包形成时就确定)
    private Long receiveAmount;

    // 创建时间
    private Date createDatetime;

    // 备注
    private String remark;

    // 状态
    private String status;

    // 接收人
    private String receiver;

    // 接收时间
    private Date receiveDatetime;

    // 系统编号
    private String systemCode;

    // 公司编号
    private String companyCode;

    // *************db properties ****************

    // 创建日期起
    private Date createDatetimeStart;

    // 创建日期止
    private Date createDatetimeEnd;

    // 接收日期起
    private Date receiveDatetimeStart;

    // 接收日期止
    private Date receiveDatetimeEnd;

    // 树主人用户
    private User ownerUser;

    // 接收人用户
    private User receiverUser;

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
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

    public Date getReceiveDatetimeStart() {
        return receiveDatetimeStart;
    }

    public void setReceiveDatetimeStart(Date receiveDatetimeStart) {
        this.receiveDatetimeStart = receiveDatetimeStart;
    }

    public Date getReceiveDatetimeEnd() {
        return receiveDatetimeEnd;
    }

    public void setReceiveDatetimeEnd(Date receiveDatetimeEnd) {
        this.receiveDatetimeEnd = receiveDatetimeEnd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwnerCurrency(String ownerCurrency) {
        this.ownerCurrency = ownerCurrency;
    }

    public String getOwnerCurrency() {
        return ownerCurrency;
    }

    public void setReceiveCurrency(String receiveCurrency) {
        this.receiveCurrency = receiveCurrency;
    }

    public String getReceiveCurrency() {
        return receiveCurrency;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiveDatetime(Date receiveDatetime) {
        this.receiveDatetime = receiveDatetime;
    }

    public Date getReceiveDatetime() {
        return receiveDatetime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Long getOwnerAmount() {
        return ownerAmount;
    }

    public void setOwnerAmount(Long ownerAmount) {
        this.ownerAmount = ownerAmount;
    }

    public Long getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Long receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getHzbCode() {
        return hzbCode;
    }

    public void setHzbCode(String hzbCode) {
        this.hzbCode = hzbCode;
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

}
