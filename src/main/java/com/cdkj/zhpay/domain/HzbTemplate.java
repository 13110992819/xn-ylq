package com.cdkj.zhpay.domain;

import java.util.Date;

import com.cdkj.zhpay.dao.base.ABaseDO;

/**
 * 汇赚宝模板
 * @author: xieyj 
 * @since: 2017年3月20日 下午7:45:25 
 * @history:
 */
public class HzbTemplate extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 名称
    private String name;

    // 照片
    private String pic;

    // 价格
    private Long price;

    // 价格币种
    private String currency;

    // 周期内可被摇总次数
    private Integer periodRockNum;

    // 可摇总次数
    private Integer totalRockNum;

    // 价值1
    private Long backAmount1;

    // 价值2
    private Long backAmount2;

    // 价值3
    private Long backAmount3;

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

    // 公司编号
    private String companyCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

}
