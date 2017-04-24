package com.cdkj.zhpay.dto.req;

import java.util.List;

public class XN615112Req {
    // 用户列表编号(必填)
    private List<String> userIdList;

    // 汇赚宝模板编号(必填)
    private String hzbTemplateCode;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getHzbTemplateCode() {
        return hzbTemplateCode;
    }

    public void setHzbTemplateCode(String hzbTemplateCode) {
        this.hzbTemplateCode = hzbTemplateCode;
    }
}
