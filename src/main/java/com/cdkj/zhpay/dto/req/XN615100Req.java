/**
 * @Title XN615100Req.java 
 * @Package com.cdkj.zhpay.dto.req 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午8:18:45 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: xieyj 
 * @since: 2017年3月20日 下午8:18:45 
 * @history:
 */
public class XN615100Req {

    // 名称(必填)
    private String name;

    // 照片(必填)
    private String pic;

    // 价格(必填)
    private String price;

    // 价格币种(必填)
    private String currency;

    // 周期内可被摇总次数(选填)
    private String periodRockNum;

    // 可摇总次数(选填)
    private String totalRockNum;

    // 价值1(选填)
    private String backAmount1;

    // 价值2(选填)
    private String backAmount2;

    // 价值3(选填)
    private String backAmount3;

    // 更新人(必填)
    private String updater;

    // 备注(选填)
    private String remark;

    // 系统编号(必填)
    private String systemCode;

    // 公司编号(必填)
    private String companyCode;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPeriodRockNum() {
        return periodRockNum;
    }

    public void setPeriodRockNum(String periodRockNum) {
        this.periodRockNum = periodRockNum;
    }

    public String getTotalRockNum() {
        return totalRockNum;
    }

    public void setTotalRockNum(String totalRockNum) {
        this.totalRockNum = totalRockNum;
    }

    public String getBackAmount1() {
        return backAmount1;
    }

    public void setBackAmount1(String backAmount1) {
        this.backAmount1 = backAmount1;
    }

    public String getBackAmount2() {
        return backAmount2;
    }

    public void setBackAmount2(String backAmount2) {
        this.backAmount2 = backAmount2;
    }

    public String getBackAmount3() {
        return backAmount3;
    }

    public void setBackAmount3(String backAmount3) {
        this.backAmount3 = backAmount3;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

}
