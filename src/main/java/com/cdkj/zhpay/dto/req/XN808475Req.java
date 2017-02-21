package com.cdkj.zhpay.dto.req;


public class XN808475Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 树主人（选填）
    private String owner;

    // 接收人（选填）
    private String receiver;

    // 状态（选填）
    private String status;

    // 创建日期起（选填）
    private String createDatetimeStart;

    // 创建日期止（选填）
    private String createDatetimeEnd;

    // 接收日期起（选填）
    private String receiveDatetimeStart;

    // 接收日期止（选填）
    private String receiveDatetimeEnd;

    public String getCreateDatetimeStart() {
        return createDatetimeStart;
    }

    public void setCreateDatetimeStart(String createDatetimeStart) {
        this.createDatetimeStart = createDatetimeStart;
    }

    public String getCreateDatetimeEnd() {
        return createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(String createDatetimeEnd) {
        this.createDatetimeEnd = createDatetimeEnd;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiveDatetimeStart() {
        return receiveDatetimeStart;
    }

    public void setReceiveDatetimeStart(String receiveDatetimeStart) {
        this.receiveDatetimeStart = receiveDatetimeStart;
    }

    public String getReceiveDatetimeEnd() {
        return receiveDatetimeEnd;
    }

    public void setReceiveDatetimeEnd(String receiveDatetimeEnd) {
        this.receiveDatetimeEnd = receiveDatetimeEnd;
    }
}
