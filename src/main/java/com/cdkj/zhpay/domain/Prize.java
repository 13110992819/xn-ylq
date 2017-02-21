/**
 * @Title Prize.java 
 * @Package com.xnjr.mall.domain 
 * @Description 
 * @author xieyj  
 * @date 2017年1月10日 上午11:13:32 
 * @version V1.0   
 */
package com.cdkj.zhpay.domain;

import com.cdkj.zhpay.dao.base.ABaseDO;

/** 
 * 奖品
 * @author: xieyj 
 * @since: 2017年1月10日 上午11:13:32 
 * @history:
 */
public class Prize extends ABaseDO {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1L;

    // 类型(1 钱包 2 购物币 3 红包)
    private String type;

    // 奖品权重
    private Double weight;

    public Prize() {
    }

    public Prize(String type, Double weight) {
        this.type = type;
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
