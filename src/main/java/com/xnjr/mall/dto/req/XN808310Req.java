package com.xnjr.mall.dto.req;

/**
 * 宝贝分页查询
 * @author: shan 
 * @since: 2016年12月20日 下午6:00:23 
 * @history:
 */
public class XN808310Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 商铺编号
    private String storeCode;

    // 宝贝名称
    private String name;

    // 状态
    private String status;

    // 系统编号
    private String systemCode;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

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

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}
