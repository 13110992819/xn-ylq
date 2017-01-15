package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelRecordNumber;

/**
 * @author: xieyj 
 * @since: 2017年1月13日 下午3:03:41 
 * @history:
 */
public interface IJewelRecordNumberAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    /**
     * 
     * @param start
     * @param limit
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:18:29 shan
     * @history:
     */
    public Paginable<JewelRecordNumber> queryJewelRecordNumberPage(int start,
            int limit, JewelRecordNumber condition);

    /**
     * 
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:18:32 shan
     * @history:
     */
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber condition);

    /**
     * 
     * @param id
     * @return 
     * @create: 2016年12月20日 下午12:18:36 shan
     * @history:
     */
    public JewelRecordNumber getJewelRecordNumber(Long id);

}
