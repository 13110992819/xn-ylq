package com.xnjr.mall.domain;

import java.util.Date;

import com.xnjr.mall.dao.base.ABaseDO;

/**
* 汇赚宝摇一摇
* @author: xieyj 
* @since: 2017年01月10日 10:50:24
* @history:
*/
public class HzbYy extends ABaseDO {

    private static final long serialVersionUID = 1L;

    private String code;

    private Long hzbHoldId;

    // 类型(1 红包 2 钱包 3 购物币)
    private String type;

    private Integer quantity;

    private String userId;

    private String deviceNo;

    private Date createDatetime;

    // **********db properties********
    private Date createDatetimeStart;

    private Date createDatetimeEnd;

    public Date getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(Date createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public Date getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setHzbHoldId(Long hzbHoldId) {
        this.hzbHoldId = hzbHoldId;
    }

    public Long getHzbHoldId() {
        return hzbHoldId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

}
