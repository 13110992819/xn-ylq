/**
 * @Title UserExt.java 
 * @Package com.std.user.domain 
 * @Description 
 * @author xieyj  
 * @date 2016年9月18日 上午10:45:44 
 * @version V1.0   
 */
package com.cdkj.zhpay.domain;

import com.cdkj.zhpay.dao.base.ABaseDO;

/** 
 * 用户扩展表
 * @author: xieyj 
 * @since: 2016年9月18日 上午10:45:44 
 * @history:
 */
public class UserExt extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 用户编号
    private String userId;

    // 省
    private String province;

    // 市区
    private String city;

    // 区(县)
    private String area;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
