package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.User;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:23:56 
 * @history:
 */
public interface IJewelRecordBO extends IPaginableBO<JewelRecord> {

    /**
     * 验证是否大于最大次数
     * @param user
     * @param jewel
     * @param times 
     * @create: 2017年3月23日 下午1:15:09 myb858
     * @history:
     */
    public void checkTimes(User user, Jewel jewel, Integer times);

    /**
     * 保存宝贝
     * @param userId
     * @param jewelCode
     * @param times
     * @param amount
     * @param ip
     * @param payGroup
     * @param systemCode
     * @return 
     * @create: 2017年4月4日 下午4:36:57 xieyj
     * @history:
     */
    public String saveJewelRecord(String userId, String jewelCode,
            Integer times, Long amount, String ip, String payGroup,
            String systemCode);

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

    public Paginable<Jewel> queryMyJewelRecordPage(int start, int pageSize,
            String userId);

    public Long getLastRecordsTimes(String jewelCode);

    public JewelRecord getWinJewelRecord(String jewelCode, String luckyNumber);

    public Long getTotalAmount(String payGroup);

    public JewelRecord getJewelRecordByPayGroup(String payGroup);

}
