package com.cdkj.zhpay.dto.req;

/**
 * 发货
 * @author: xieyj 
 * @since: 2017年1月13日 上午10:33:53 
 * @history:
 */
public class XN808304Req {
    // 宝贝编号(必填)
    public String code;

    // 更新人编号(必填)
    public String updater;

    // 物流信息(必填)
    public String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
