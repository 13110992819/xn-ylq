package com.xnjr.mall.dto.req;

/**
 * 宝贝列表查询
 * @author: asus 
 * @since: 2016年12月21日 下午4:38:44 
 * @history:
 */
public class XN808311Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 模板编号
    private String templateCode;

    // 状态
    private String status;

    // 系统编号
    private String systemCode;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

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
