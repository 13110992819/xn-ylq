/**
 * @Title XNlh5034Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年6月12日 下午1:36:37 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2016年6月12日 下午1:36:37 
 * @history:
 */
public class XN808917Req {
    // 配置文件名(必填)
    private String key;

    // 公司编号(必填)
    private String companyCode;

    // 系统编号(必填)
    private String systemCode;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
