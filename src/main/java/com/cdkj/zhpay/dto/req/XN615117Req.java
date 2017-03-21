/**
 * @Title XN808454Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年12月21日 下午3:40:16 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * 周边汇赚宝列表查询
 * @author: haiqingzheng 
 * @since: 2016年12月21日 下午3:40:16 
 * @history:
 */
public class XN615117Req {

    // 经度(必填)
    private String longitude;

    // 纬度(必填)
    private String latitude;

    // 用户编号(必填)
    private String userId;

    // 设备编号(必填)
    private String deviceNo;

    // 系统编号(必填)
    private String systemCode;

    // 公司编号(必填)
    private String companyCode;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
