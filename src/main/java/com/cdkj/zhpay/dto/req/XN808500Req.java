package com.cdkj.zhpay.dto.req;

/**
 * @author: xieyj 
 * @since: 2017年1月4日 下午10:10:02 
 * @history:
 */
public class XN808500Req {
    // 系统编号(必填)
    private String systemCode;

    // 流水编号(必填)
    private String code;

    // 业务类型(必填)
    private String bizType;

    // 审批结果
    private String approveResult;

    // 审批人
    private String approver;

    // 审批说明
    private String approveNote;

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }
}
