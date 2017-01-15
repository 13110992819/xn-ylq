package com.xnjr.mall.bo;

import java.util.Date;
import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelRecord;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:23:56 
 * @history:
 */
public interface IJewelRecordBO extends IPaginableBO<JewelRecord> {
    /**
     * 
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:26:40 shan
     * @history:
     */
    public boolean isJewelRecordExist(String code);

    /**
     * 
     * @param data
     * @return 
     * @create: 2016年12月20日 下午12:26:45 shan
     * @history:
     */
    public String saveJewelRecord(JewelRecord data);

    /**
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:26:49 shan
     * @history:
     */
    public int removeJewelRecord(String code);

    public int refreshStatus(String code, String status, String remark);

    /**
     * 支付成功之后更新状态
     * @param code
     * @param status
     * @param remark
     * @return 
     * @create: 2017年1月12日 下午9:52:10 xieyj
     * @history:
     */
    public int refreshPaySuccess(String code, String status, String remark);

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
     * 填写收件地址
     * @param code
     * @param receiver
     * @param reMobile
     * @param reAddress
     * @return 
     * @create: 2017年1月13日 下午2:20:31 xieyj
     * @history:
     */
    public int refreshReAddress(String code, String receiver, String reMobile,
            String reAddress);

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

    public Long getLastRecordsTimes(String jewelCode, Date curCreateDatetime);
}
