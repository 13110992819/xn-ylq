package com.cdkj.zhpay.domain;

import java.util.Date;
import java.util.List;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
* 汇赚宝
* @author: xieyj 
* @since: 2016年12月21日 13:31:59
* @history:
*/
public class Hzb extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 用户编号
    private String userId;

    // 汇赚宝模板编号
    private String templateCode;

    // 购买时价格
    private Long price;

    // 购买时价格币种
    private String currency;

    // 周期内已被摇总次数
    private Integer periodRockNum;

    // 已摇总次数
    private Integer totalRockNum;

    // 已价值1
    private Long backAmount1;

    // 已价值2
    private Long backAmount2;

    // 已价值3
    private Long backAmount3;

    // 生成时间
    private Date createDatetime;

    // 状态
    private String status;

    // 支付组号
    private String payGroup;

    // 橙账本流水号
    private String payCode;

    // 支付时间
    private Date payDatetime;

    // 支付人民币
    private Long payAmount1;

    // 支付虚拟币1
    private Long payAmount2;

    // 支付虚拟币2
    private Long payAmount3;

    // 系统编号
    private String systemCode;

    // 公司编号
    private String companyCode;

    // **************db properties*******************
    // 摇的人的经度
    private String userLatitude;

    // 摇的人纬度
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

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
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

    public Long getBackAmount1() {
        return backAmount1;
    }

    public void setBackAmount1(Long backAmount1) {
        this.backAmount1 = backAmount1;
    }

    public Long getBackAmount2() {
        return backAmount2;
    }

    public void setBackAmount2(Long backAmount2) {
        this.backAmount2 = backAmount2;
    }

    public Long getBackAmount3() {
        return backAmount3;
    }

    public void setBackAmount3(Long backAmount3) {
        this.backAmount3 = backAmount3;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
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

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
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
