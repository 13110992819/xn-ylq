package com.cdkj.zhpay.dto.req;

public class XN808460Req {
    // 用户编号(必填)
    private String userId;

    // 针对汇赚宝的编号(必填)
    private String hzbCode;

    // 设备编号(必填)
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

    public String getHzbCode() {
        return hzbCode;
    }

    public void setHzbCode(String hzbCode) {
        this.hzbCode = hzbCode;
    }

}
