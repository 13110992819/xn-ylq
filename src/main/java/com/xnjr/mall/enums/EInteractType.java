/**
 * @Title EInteractType.java 
 * @Package com.xnjr.mall.enums 
 * @Description 
 * @author haiqingzheng  
 * @date 2017年1月17日 下午6:03:14 
 * @version V1.0   
 */
package com.xnjr.mall.enums;

/** 
 * @author: haiqingzheng 
 * @since: 2017年1月17日 下午6:03:14 
 * @history:
 */
public enum EInteractType {
    // 1
    HAOPING("1", "好评");

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
