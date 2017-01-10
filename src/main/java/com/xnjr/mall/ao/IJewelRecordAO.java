package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelRecord;

/**
 * 
 * @author: shan 
 * @since: 2016年12月20日 下午12:08:57 
 * @history:
 */
public interface IJewelRecordAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 参与夺宝
     * @param data
     * @return 
     * @create: 2016年12月20日 下午12:11:39 shan
     * @history:
     */
    public String buy(String userId, String jewelCode, Integer times);

    /**
     * 追加
     * @param jewelRecordCode
     * @param times
     * @return 
     * @create: 2017年1月10日 下午8:19:35 haiqingzheng
     * @history:
     */
    public void additionalBuy(String jewelRecordCode, Integer times);

    /**
     * 修改标的购买记录
     * @param data 
     * @create: 2016年12月20日 下午12:11:42 shan
     * @history:
     */
    public void editJewelRecord(JewelRecord data);

    /**
     * 删除标的购买记录
     * @param code
     * @create: 2016年12月20日 下午12:11:45 shan
     * @history:
     */
    public void dropJewelRecord(String code);

    /**
     * 标的购买记录分页
     * @param start
     * @param limit
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:11:49 shan
     * @history:
     */
    public Paginable<JewelRecord> queryJewelRecordPage(int start, int limit,
            JewelRecord condition);

    public Paginable<JewelRecord> queryMyJewelRecordPage(int start, int limit,
            JewelRecord condition);

    /**
     * 查询所有标的购买记录
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:11:53 shan
     * @history:
     */
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition);

    /**
     * 查询标的购买详情
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:14:53 shan
     * @history:
     */
    public JewelRecord getJewelRecord(String code, String userId);

}
