package com.xnjr.mall.dto.req;

/**
 * 申请宝贝
 * @author: shan 
 * @since: 2016年12月20日 下午1:58:37 
 * @history:
 */
public class XN808300Req {
    // 商家编号(必填)
    private String storeCode;

    // 宝贝名称(必填)
    private String name;

    // 广告语(必填)
    private String slogan;

    // 广告图片(必填)
    private String advPic;

    // 图文描述(必填)
    private String description;

    // 系统编号(必填)
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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAdvPic() {
        return advPic;
    }

    public void setAdvPic(String advPic) {
        this.advPic = advPic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}
