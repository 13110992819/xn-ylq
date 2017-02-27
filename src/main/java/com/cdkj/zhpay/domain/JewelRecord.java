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

    // 支付金额
    private Long payAmount;

    // 支付时间
    private Date payDatetime;

    // 状态(0待开奖，1已中奖，2未中奖)
    private String status;

    // 备注
    private String remark;

    // ip
    private String ip;

    // 支付组号
    private String payGroup;

    // 支付编号
    private String payCode;

    // 系统编号
    private String systemCode;

    // ***********************db properties **************************
    // 昵称
    private String nickname;

    // 手机号
    private String mobile;

    // 照片
    private String photo;

    // 宝贝购买记录
    private List<JewelRecordNumber> jewelRecordNumberList;

    // 宝贝
    private Jewel jewel;

    // 我投资人次
    private String myInvestTimes;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public JewelRecord() {
        Jewel jewel = new Jewel();
        this.jewel = jewel;
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

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<JewelRecordNumber> getJewelRecordNumberList() {
        return jewelRecordNumberList;
    }

    public void setJewelRecordNumberList(
            List<JewelRecordNumber> jewelRecordNumberList) {
        this.jewelRecordNumberList = jewelRecordNumberList;
    }

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }

    public String getMyInvestTimes() {
        return myInvestTimes;
    }

    public void setMyInvestTimes(String myInvestTimes) {
        this.myInvestTimes = myInvestTimes;
    }

}
