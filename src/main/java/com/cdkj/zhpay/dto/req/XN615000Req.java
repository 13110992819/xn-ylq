/**
 * @Title XN808350Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午12:45:24 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

import java.util.Date;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午12:45:24 
 * @history:
 */
public class XN615000Req {
    // 中奖币种（必填）
    private String currency;

    // 中奖金额（必填）
    private String amount;

    // 总人次（必填）
    private String totalNum;

    // 人次单价（必填）
    private String price;

    // 单人最大投资次数（必填）
    private String maxInvestNum;

    // 宣传文字（必填）
    private String advText;

    // 宣传图（必填）
    private String advPic;

    // 更新人（必填）
    private String updater;

    // 备注（必填）
    private String remark;

    // 系统编号（必填）
    private String systemCode;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaxInvestNum() {
        return maxInvestNum;
    }

    public void setMaxInvestNum(String maxInvestNum) {
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

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}
