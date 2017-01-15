package com.xnjr.mall.domain;

import java.util.Date;
import java.util.List;

import com.xnjr.mall.dao.base.ABaseDO;

/**
* 股份购买记录
* @author: xieyj 
* @since: 2016年12月19日 14:18:40
* @history:
*/
public class StockHold extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private Long id;

    // 用户编号
    private String userId;

    // 股份编号
    private String stockCode;

    // 状态（待支付/未清算/已清算）
    private String status;

    // 已经返还次数
    private Integer backNum;

    // 已返福利1
    private Long backWelfare1;

    // 已返福利2
    private Long backWelfare2;

    // 下次返还时间
    private Date nextBack;

    // 系统编号
    private String systemCode;

    // 支付编号
    private String payCode;

    // 福利月卡
    private Stock stock;

    // 用户列表编号
    private List<String> userList;

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBackNum() {
        return backNum;
    }

    public void setBackNum(Integer backNum) {
        this.backNum = backNum;
    }

    public Long getBackWelfare1() {
        return backWelfare1;
    }

    public void setBackWelfare1(Long backWelfare1) {
        this.backWelfare1 = backWelfare1;
    }

    public Long getBackWelfare2() {
        return backWelfare2;
    }

    public void setBackWelfare2(Long backWelfare2) {
        this.backWelfare2 = backWelfare2;
    }

    public Date getNextBack() {
        return nextBack;
    }

    public void setNextBack(Date nextBack) {
        this.nextBack = nextBack;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
