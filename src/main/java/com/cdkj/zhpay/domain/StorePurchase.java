package com.cdkj.zhpay.domain;

import java.util.Date;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
* 店铺买单
* @author: xieyj 
* @since: 2016年12月19日 00:42:58
* @history:
*/
public class StorePurchase extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 用户编号
    private String userId;

    // 支付方式
    private String payType;

    // 消费金额
    private Long purchaseAmount;

    // 人民币消费金额
    private Long amount1;

    // 虚拟币1消费金额
    private Long amount2;

    // 虚拟币2消费金额
    private Long amount3;

    // 返现金额
    private Long backAmount;

    // 折扣券编号
    private String ticketCode;

    // 状态
    private String status;

    // 消费时间
    private Date createDatetime;

    // 实际支付时间
    private Date payDatetime;

    // 备注
    private String remark;

    // 商户编号
    private String storeCode;

    // 系统编号
    private String systemCode;

    // 流水编号
    private String jourCode;

    // *************db properties****************
    private Store store;

    private StoreTicket storeTicket;

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public StoreTicket getStoreTicket() {
        return storeTicket;
    }

    public void setStoreTicket(StoreTicket storeTicket) {
        this.storeTicket = storeTicket;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAmount1() {
        return amount1;
    }

    public void setAmount1(Long amount1) {
        this.amount1 = amount1;
    }

    public Long getAmount2() {
        return amount2;
    }

    public void setAmount2(Long amount2) {
        this.amount2 = amount2;
    }

    public Long getAmount3() {
        return amount3;
    }

    public void setAmount3(Long amount3) {
        this.amount3 = amount3;
    }

    public Long getBackAmount() {
        return backAmount;
    }

    public void setBackAmount(Long backAmount) {
        this.backAmount = backAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getJourCode() {
        return jourCode;
    }

    public void setJourCode(String jourCode) {
        this.jourCode = jourCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
