package com.cdkj.zhpay.domain;

import java.util.List;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
* 汇赚宝
* @author: xieyj 
* @since: 2016年12月21日 13:31:59
* @history:
*/
public class HzbHold extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private Long id;

    // 用户编号
    private String userId;

    // 汇赚宝编号
    private String hzbCode;

    // 状态
    private String status;

    // 购买价格
    private Long price;

    // 价格币种
    private String currency;

    // 周期内被摇总次数
    private Integer periodRockNum;

    // 已摇总次数
    private Integer totalRockNum;

    // 支付价格1人民币
    private Long payAmount1;

    // 支付价格2人民币
    private Long payAmount2;

    // 支付价格3人民币
    private Long payAmount3;

    // 支付编号
    private String payCode;

    // 系统编号
    private String systemCode;

    // **************db properties*******************
    // 精度
    private String userLatitude;

    // 维度
    private String userLongitude;

    // 距离
    private String distance;

    // 分享URL
    private String shareUrl;

    // 手机号
    private String mobile;

    // 用户列表编号
    private List<String> userList;

    public Long getPayAmount1() {
        return payAmount1;
    }

    public void setPayAmount1(Long payAmount1) {
        this.payAmount1 = payAmount1;
    }

    public Long getPayAmount2() {
        return payAmount2;
    }

    public void setPayAmount2(Long payAmount2) {
        this.payAmount2 = payAmount2;
    }

    public Long getPayAmount3() {
        return payAmount3;
    }

    public void setPayAmount3(Long payAmount3) {
        this.payAmount3 = payAmount3;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
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

    public String getHzbCode() {
        return hzbCode;
    }

    public void setHzbCode(String hzbCode) {
        this.hzbCode = hzbCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPeriodRockNum() {
        return periodRockNum;
    }

    public void setPeriodRockNum(Integer periodRockNum) {
        this.periodRockNum = periodRockNum;
    }

    public Integer getTotalRockNum() {
        return totalRockNum;
    }

    public void setTotalRockNum(Integer totalRockNum) {
        this.totalRockNum = totalRockNum;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}