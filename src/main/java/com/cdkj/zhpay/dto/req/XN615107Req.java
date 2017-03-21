/**
 * @Title XN615107Req.java 
 * @Package com.cdkj.zhpay.dto.req 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午8:19:39 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: xieyj 
 * @since: 2017年3月20日 下午8:19:39 
 * @history:
 */
public class XN615107Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -5315928105680332449L;

    // 名称
    private String name;

    // 状态（0 待上架 1 已上架 2 已下架）
    private String status;

    // 更新人
    private String updater;

    // 系统编号
    private String systemCode;

    // 公司编号
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
