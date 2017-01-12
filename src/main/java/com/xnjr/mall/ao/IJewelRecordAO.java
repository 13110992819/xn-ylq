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
     * 购买宝贝
     * @param userId
     * @param jewelCode
     * @param times
     * @param payType
     * @param ip
     * @return 
     * @create: 2017年1月11日 下午7:35:31 xieyj
     * @history:
     */
    public Object buyJewel(String userId, String jewelCode, Integer times,
            String payType, String ip);

    // /**
    // * 追加购买宝贝记录
    // * @param jewelRecordCode
    // * @param times
    // * @param payType
    // * @param ip
    // * @create: 2017年1月12日 上午10:44:54 xieyj
    // * @history:
    // */
    // public void additionalBuy(String jewelRecordCode, Integer times,
    // String payType, String ip);

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
     * 根据流水编号，找到对应消费记录，更新支付状态
     * @param payCode
     * @return 
     * @create: 2017年1月10日 下午7:48:09 xieyj
     * @history:
     */
    public void paySuccess(String payCode);

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
