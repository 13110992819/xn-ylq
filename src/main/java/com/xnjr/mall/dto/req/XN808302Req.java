package com.xnjr.mall.dto.req;

/**
 * 宝贝重提
 * @author: shan 
 * @since: 2016年12月20日 下午3:12:32 
 * @history:
 */
public class XN808302Req {
    // 编号(必填)
    public String code;

    // 宝贝名称(必填)
    public String name;

    // 广告语(必填)
    public String slogan;

    // 广告图片(必填)
    public String advPic;

    // 详情文本(必填)
    private String descriptionText;

    // 详情图片(必填)
    private String descriptionPic;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

}
