package com.cdkj.zhpay.dto.res;

/**
 * @author: xieyj 
 * @since: 2017年2月9日 下午2:20:59 
 * @history:
 */
public class XN808325Res {
    // 分页查询好评列表
    private Object page;

    // 好评率
    private String goodRate;

    // 购买人数
    private Long buyNums;

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public String getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(String goodRate) {
        this.goodRate = goodRate;
    }

    public Long getBuyNums() {
        return buyNums;
    }

    public void setBuyNums(Long buyNums) {
        this.buyNums = buyNums;
    }

}
