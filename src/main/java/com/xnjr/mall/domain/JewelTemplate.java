package com.xnjr.mall.domain;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;

/**
* 小目标模板
* @author: haiqingzheng
* @since: 2017年02月20日 12:32:56
* @history:
*/
public class JewelTemplate extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 中奖币种
    private String currency;

    // 中奖金额
    private Long amount;

    // 总人次
    private Integer totalNum;

    // 人次单价
    private Long price;

    // 单人最大投资次数
    private Integer maxInvestNum;

    // 宣传文字
    private String advText;

    // 宣传图
    private String advPic;

    // 当前期数
    private Integer currentPeriods;

    // 状态（0 待上架 1 已上架 2 已下架）
    private String status;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    // 系统编号
    private String systemCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getMaxInvestNum() {
        return maxInvestNum;
    }

    public void setMaxInvestNum(Integer maxInvestNum) {
        this.maxInvestNum = maxInvestNum;
    }

    public String getAdvText() {
        return advText;
    }

    public void setAdvText(String advText) {
        this.advText = advText;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public Integer getCurrentPeriods() {
        return currentPeriods;
    }

    public void setCurrentPeriods(Integer currentPeriods) {
        this.currentPeriods = currentPeriods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}
