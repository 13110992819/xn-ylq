package com.xnjr.mall.dto.req;

import java.util.List;

/**
 * 宝贝审批
 * @author: shan 
 * @since: 2016年12月20日 下午3:00:22 
 * @history:
 */
public class XN808309Req {
    // 产品列表编号（必填）
    public List<String> codeList;

    // 审核结果(1 通过 0 不通过)（必填）
    public String approver;

    // 审核人（必填）
    public String approveResult;

    // 审核备注（选填）
    public String approveNote;

    public List<String> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<String> codeList) {
        this.codeList = codeList;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }

    public String getApproveNote() {
        return approveNote;
    }

    public void setApproveNote(String approveNote) {
        this.approveNote = approveNote;
    }
}
