/**
 * @Title XN808354Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月20日 下午2:49:31 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午2:49:31 
 * @history:
 */
public class XN615005Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -2065359816574498943L;

    // 状态（选填）
    private String status;

    // 更新人（选填）
    private String updater;

    // 公司编号（必填）
    private String companyCode;

    // 系统编号（必填）
    private String systemCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
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
