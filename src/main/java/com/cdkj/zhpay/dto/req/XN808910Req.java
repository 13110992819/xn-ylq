package com.cdkj.zhpay.dto.req;

public class XN808910Req {
    // 编号
    private String id;

    // 类型
    private String type;

    // 值（必填）
    private String cvalue;

    // 备注（选填）
    private String remark;

    // 公司编号（必填）
    private String companyCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCvalue() {
        return cvalue;
    }

    public void setCvalue(String cvalue) {
        this.cvalue = cvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}
