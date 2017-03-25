/**
 * @Title XNlh5014Req.java 
 * @Package com.xnjr.moom.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午8:07:53 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午8:07:53 
 * @history:
 */
public class XN002900Req {
    // 类型（第一层/第二层）（选填）
    private String type;

    // 父key（选填）
    private String parentKey;

    // key（选填）
    private String dkey;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getDkey() {
        return dkey;
    }

    public void setDkey(String dkey) {
        this.dkey = dkey;
    }

}
