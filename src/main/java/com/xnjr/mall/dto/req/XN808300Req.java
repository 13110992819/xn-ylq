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

    // 详情文本
    private String descriptionText;

    // 详情图片
    private String descriptionPic;

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

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getDescriptionPic() {
        return descriptionPic;
    }

    public void setDescriptionPic(String descriptionPic) {
        this.descriptionPic = descriptionPic;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

}
