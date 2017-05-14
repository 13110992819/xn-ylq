package com.cdkj.zhpay.ao;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:08:57 
 * @history:
 */
public interface IJewelRecordAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

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

    public Paginable<Jewel> queryMyJewelPage(int start, int limit, String userId);

    /**
     * 查询标的购买详情
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:14:53 shan
     * @history:
     */
    public JewelRecord getJewelRecord(String code);

    public boolean buyJewelByZHYE(String userId, String jewelCode,
            Integer times, String ip, String tradePwd);

    public boolean buyJewelByDYBZ(String userId, String jewelCode,
            Integer times, String ip);

    public Object buyJewelByWxApp(String userId, String jewelCode,
            Integer times, String ip);

    public Object buyJewelByWxH5(String userId, String jewelCode,
            Integer times, String ip);

    public Object buyJewelByZFB(String userId, String jewelCode, Integer times,
            String ip);

}
