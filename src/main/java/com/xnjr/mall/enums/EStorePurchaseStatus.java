/**
 * @Title EStorePurchaseStatus.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2017年1月5日 下午6:01:32 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2017年1月5日 下午6:01:32 
 * @history:
 */
public enum EStorePurchaseStatus {
    NEW("0", "待支付"), PAYED("1", "已支付"), CALCELED("2", "已取消");

    EStorePurchaseStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
