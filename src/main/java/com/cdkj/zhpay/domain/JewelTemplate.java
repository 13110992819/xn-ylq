package com.cdkj.zhpay.domain;

import java.util.Date;

import com.cdkj.zhpay.dao.base.ABaseDO;

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

    // 中奖金额
    private Long toAmount;

    // 中奖币种
    private String toCurrency;

    // 总人次
    private Integer totalNum;

    // 单人最大次数
    private Integer maxNum;

    // 人次单价
    private Long fromAmount;

    // 单价币种
    private String fromCurrency;

    // 宣传标语
    private String slogan;

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

    // 公司编号
    private String companyCode;

    // 系统编号
    private String systemCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getToAmount() {
        return toAmount;
    }

    public void setToAmount(Long toAmount) {
        this.toAmount = toAmount;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Long getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Long fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
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
}
