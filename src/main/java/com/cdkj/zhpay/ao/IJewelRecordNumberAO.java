package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.domain.JewelRecordNumber;

/**
 * @author: xieyj 
 * @since: 2017年1月13日 下午3:03:41 
 * @history:
 */
public interface IJewelRecordNumberAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    /**
     * 
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:18:32 shan
     * @history:
     */
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber condition);
}
