package com.xnjr.mall.dto.req;

/**
 * 我的夺宝号码列表查询
 * @author: asus 
 * @since: 2016年12月21日 下午5:03:34 
 * @history:
 */
public class XN808315Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = -3572987603574928980L;

    // 夺宝标的编号
    public String JewelCode;

    public String getJewelCode() {
        return JewelCode;
    }

    public void setJewelCode(String jewelCode) {
        JewelCode = jewelCode;
    }
}
