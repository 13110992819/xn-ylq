/**
 * @Title XN808354Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午2:49:31 
 * @version V1.0   
 */
package com.xnjr.mall.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午2:49:31 
 * @history:
 */
public class XN808354Req extends APageReq {

    // 状态（必填）
    private String status;

    // 系统编号（必填）
    private String systemCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
