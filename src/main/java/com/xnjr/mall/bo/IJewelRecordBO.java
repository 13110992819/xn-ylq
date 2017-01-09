package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.JewelRecord;

/**
 * 
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
     * 
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:26:49 shan
     * @history:
     */
    public int removeJewelRecord(String code);

    public int refreshStatus(String code, String status, String remark);

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
     * 
     * @param data
     * @return 
     * @create: 2016年12月20日 下午12:27:00 shan
     * @history:
     */
    public List<JewelRecord> queryJewelRecordList(JewelRecord data);
}
