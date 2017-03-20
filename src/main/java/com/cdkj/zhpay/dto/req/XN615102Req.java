/**
 * @Title XN615102Req.java 
 * @Package com.cdkj.zhpay.dto.req 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午8:18:55 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: xieyj 
 * @since: 2017年3月20日 下午8:18:55 
 * @history:
 */
public class XN615102Req {
    // 编号
    private String code;

    // 名称
    private String name;

    // 照片
    private String pic;

    // 玩法描述
    private String description;

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

    // 更新人
    private String updater;

    // 备注
    private String remark;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
