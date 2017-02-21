/**
 * @Title EInteractType.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2017年1月17日 下午6:03:14 
 * @version V1.0   
 */
package com.cdkj.zhpay.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2017年1月17日 下午6:03:14 
 * @history:
 */
public enum EInteractType {
    // 1宝贝，2商品
    JEWEL("1", "宝贝"), PRODUCT("2", "商品");

    EInteractType(String code, String value) {
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
