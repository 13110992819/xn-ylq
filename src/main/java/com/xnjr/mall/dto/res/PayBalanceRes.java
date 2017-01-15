/**
 * @Title PayBalanceRes.java 
 * @Package com.xnjr.mall.dto.res 
 * @Description 
 * @author xieyj  
 * @date 2017年1月15日 下午12:05:49 
 * @version V1.0   
 */
package com.xnjr.mall.dto.res;

/** 
 * @author: xieyj 
 * @since: 2017年1月15日 下午12:05:49 
 * @history:
 */
public class PayBalanceRes {
    // 贡献奖励
    private Long gxjlAmount;

    // 分润
    private Long frAmount;

    public PayBalanceRes() {
    }

    public PayBalanceRes(Long gxjlAmount, Long frAmount) {
        this.gxjlAmount = gxjlAmount;
        this.frAmount = frAmount;
    }

    public Long getGxjlAmount() {
        return gxjlAmount;
    }

    public void setGxjlAmount(Long gxjlAmount) {
        this.gxjlAmount = gxjlAmount;
    }

    public Long getFrAmount() {
        return frAmount;
    }

    public void setFrAmount(Long frAmount) {
        this.frAmount = frAmount;
    }
}
