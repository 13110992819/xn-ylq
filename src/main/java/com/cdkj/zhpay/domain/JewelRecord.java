package com.cdkj.zhpay.domain;

import java.util.Date;
import java.util.List;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
 * 标的购买
 * @author: shan 
 * @since: 2016年12月19日 下午8:49:19 
 * @history:
 */
public class JewelRecord extends ABaseDO {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 用户编号
    private String userId;

    // 宝贝编号
    private String jewelCode;

    // 投资时间
    private Date investDatetime;

    // 参与次数
    private Integer times;

    // ip
    private String ip;

    // 状态(0待开奖，1已中奖，2未中奖)
    private String status;

    // 支付组号
    private String payGroup;

    // 支付编号
    private String payCode;

    // 支付金额
    private Long payAmount;

    // 支付时间(格式化到毫秒)
    private String payDatetime;

    // 公司编号
    private String companyCode;

    // 系统编号
    private String systemCode;

    // 对应宝贝
    private Jewel jewel;

    // 本次购买记录对应号码
    private List<JewelRecordNumber> jewelRecordNumberList;

    // 购买用户
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }

    public List<JewelRecordNumber> getJewelRecordNumberList() {
        return jewelRecordNumberList;
    }

    public void setJewelRecordNumberList(
            List<JewelRecordNumber> jewelRecordNumberList) {
        this.jewelRecordNumberList = jewelRecordNumberList;
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

    public String getJewelCode() {
        return jewelCode;
    }

    public void setJewelCode(String jewelCode) {
        this.jewelCode = jewelCode;
    }

    public Date getInvestDatetime() {
        return investDatetime;
    }

    public void setInvestDatetime(Date investDatetime) {
        this.investDatetime = investDatetime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayGroup() {
        return payGroup;
    }

    public void setPayGroup(String payGroup) {
        this.payGroup = payGroup;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    // ***********************db properties **************************
    //
    // // 我投资人次
    // private String myInvestTimes;
    //
    //

}
