package com.cdkj.zhpay.dto.req;

public class XN808460Req {
    // 用户编号
    private String userId;

    // 针对持有汇赚宝序号
    private String hzbHoldId;

    // 设备编号
    private String deviceNo;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHzbHoldId() {
        return hzbHoldId;
    }

    public void setHzbHoldId(String hzbHoldId) {
        this.hzbHoldId = hzbHoldId;
    }
}
