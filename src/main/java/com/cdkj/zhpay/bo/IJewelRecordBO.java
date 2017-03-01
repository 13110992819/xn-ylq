package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelRecord;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:23:56 
 * @history:
 */
public interface IJewelRecordBO extends IPaginableBO<JewelRecord> {
    /**
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:26:40 shan
     * @history:
     */
    public boolean isJewelRecordExist(String code);

    /**
     * 验证是否大于最大次数
     * @param userId
     * @param jewelCode
     * @param maxInvesttimes
     * @param times 
     * @create: 2017年2月21日 下午8:08:01 haiqingzheng
     * @history:
     */
    public void checkTimes(String userId, String jewelCode,
            Integer maxInvestTimes, Integer times);

    public String saveJewelRecord(String userId, String jewelCode,
            Integer times, Long amount, String ip, String payGroup,
            String systemCode);

    public String saveJewelRecord(String userId, String jewelCode,
            Integer times, Long amount, String ip, String systemCode);

    /**
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:26:49 shan
     * @history:
     */
    public int removeJewelRecord(String code);

    /**
     * 更新状态
     * @param code
     * @param status
     * @param remark
     * @return 
     * @create: 2017年2月21日 下午7:33:44 xieyj
     * @history:
     */
    public int refreshStatus(String code, String status, String remark);

    /**
     * 支付之后更新状态
     * @param code
     * @param status
     * @param payCode
     * @param remark
     * @return 
     * @create: 2017年2月27日 下午8:31:28 xieyj
     * @history:
     */
    public int refreshPayStatus(String code, String status, String payCode,
            String remark);

    /**
     * 将所有未中奖记录修改状态
     * @param code
     * @param jewelCode
     * @param status
     * @param remark
     * @return 
     * @create: 2017年1月9日 下午7:33:33 haiqingzheng
     * @history:
     */
    public int refreshLostInfo(String code, String jewelCode, String status,
            String remark);

    /**
     * 
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:26:56 shan
     * @history:
     */
    public JewelRecord getJewelRecord(String code);

    /**
     * @param condition
     * @return 
     * @create: 2017年1月12日 下午2:17:20 xieyj
     * @history:
     */
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition);

    /**
     * @param condition
     * @return 
     * @create: 2017年1月12日 下午2:17:25 xieyj
     * @history:
     */
    public List<JewelRecord> queryMyJewelRecordList(JewelRecord condition);

    public Paginable<JewelRecord> queryMyJewelRecordPage(int start,
            int pageSize, JewelRecord condition);

    public Long getLastRecordsTimes(String jewelCode);

    public JewelRecord getWinJewelRecord(String jewelCode, String luckyNumber);

    public Long getTotalAmount(String payGroup);
}
