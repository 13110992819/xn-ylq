package com.xnjr.mall.domain;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;

/**
 * 夺宝标的
 * @author: shan 
 * @since: 2016年12月19日 下午2:07:02 
 * @history:
 */
public class Jewel extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 商家编号
    private String storeCode;

    // 名称
    private String name;

    // 标语
    private String slogan;

    // 广告图
    private String advPic;

    // 图文描述
    private String description;

    // 单价1
    private Long price1;

    // 单价2
    private Long price2;

    // 单价3
    private Long price3;

    // 所需总人次
    private Integer totalNum;

    // 已投人次
    private Integer investNum;

    // 夺宝开始时间
    private Date startDatetime;

    // 募集天数
    private Integer raiseDays;

    // 中奖号码
    private String winNumber;

    // 中奖人编号
    private String winUserId;

    // 抽奖算法
    private String lotAlg;

    // 状态（0 待审批，1 募集中，2 审批不通过，3 到期，4 待发货，5 已发货）
    private String status;

    // 系统编号
    private String systemCode;

    // 审批人
    private String approver;

    // 审批时间
    private Date approveDatetime;

    // 审批人
    private String updater;

    // 审批时间
    private Date updateDatetime;

    // 备注
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice1() {
        return price1;
    }

    public void setPrice1(Long price1) {
        this.price1 = price1;
    }

    public Long getPrice2() {
        return price2;
    }

    public void setPrice2(Long price2) {
        this.price2 = price2;
    }

    public Long getPrice3() {
        return price3;
    }

    public void setPrice3(Long price3) {
        this.price3 = price3;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Integer investNum) {
        this.investNum = investNum;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Integer getRaiseDays() {
        return raiseDays;
    }

    public void setRaiseDays(Integer raiseDays) {
        this.raiseDays = raiseDays;
    }

    public String getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }

    public String getWinUserId() {
        return winUserId;
    }

    public void setWinUserId(String winUserId) {
        this.winUserId = winUserId;
    }

    public String getLotAlg() {
        return lotAlg;
    }

    public void setLotAlg(String lotAlg) {
        this.lotAlg = lotAlg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Date getApproveDatetime() {
        return approveDatetime;
    }

    public void setApproveDatetime(Date approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
