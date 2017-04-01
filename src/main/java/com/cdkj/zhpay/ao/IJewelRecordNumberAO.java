package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelRecordNumber;

/**
 * @author: xieyj 
 * @since: 2017年1月13日 下午3:03:41 
 * @history:
 */
public interface IJewelRecordNumberAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber condition);

    public Paginable<JewelRecordNumber> queryJewelRecordNumberPage(int start,
            int limit, JewelRecordNumber condition);

}
