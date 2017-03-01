package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelRecord;

/**
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

    /**
     * 根据支付组号，找到对应消费记录，更新支付状态
     * @param payGroup
     * @param payCode
     * @param transAmount 
     * @create: 2017年2月27日 下午8:32:54 xieyj
     * @history:
     */
    public void paySuccess(String payGroup, String payCode, Long transAmount);

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
    public JewelRecord getJewelRecord(String code);

}
