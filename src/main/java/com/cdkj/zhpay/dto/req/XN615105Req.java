/**
 * @Title XN615105Req.java 
 * @Package com.cdkj.zhpay.dto.req 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午8:19:22 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: xieyj 
 * @since: 2017年3月20日 下午8:19:22 
 * @history:
 */
public class XN615105Req extends APageReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -7794788977762516920L;

    // 名称(选填)
    private String name;

    // 状态(选填)
    private String status;

    // 更新人(选填)
    private String updater;

    // 系统编号(必填)
    private String systemCode;

    // 公司编号(必填)
    private String companyCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

}
