/**
 * @Title XNlh5010Req.java 
 * @Package com.xnjr.moom.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午5:00:54 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午5:00:54 
 * @history:
 */
public class XN808900Req {

    // 类型（第一层/第二层）（必填）
    private String type;

    // 父key（第二层必填）
    private String parentKey;

    // key（必填）
    private String dkey;

    // value（必填）
    private String dvalue;

    // 备注（选填）
    private String remark;

    // 系统编号(必填)
    private String systemCode;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

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

    public String getDvalue() {
        return dvalue;
    }

    public void setDvalue(String dvalue) {
        this.dvalue = dvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}