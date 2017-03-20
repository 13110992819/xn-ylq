/**
 * @Title XN615104Req.java 
 * @Package com.cdkj.zhpay.dto.req 
 * @Description 
 * @author xieyj  
 * @date 2017年3月20日 下午8:19:13 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.req;

/** 
 * @author: xieyj 
 * @since: 2017年3月20日 下午8:19:13 
 * @history:
 */
public class XN615104Req {
    // 编号（必填）
    private String code;

    // 更新人（必填）
    private String updater;

    // 备注（选填）
    private String remark;

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
