package com.xnjr.mall.dao;

import java.util.List;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.JewelRecordNumber;

/**
 * 
 * @author: shan 
 * @since: 2016年12月20日 上午11:33:43 
 * @history:
 */
public interface IJewelRecordNumberDAO extends IBaseDAO<JewelRecordNumber> {
    String NAMESPACE = IJewelRecordNumberDAO.class.getName().concat(".");

    /**
     * 更新标的记录号码
     * @param data
     * @return 
     * @create: 2016年12月20日 上午11:34:41 shan
     * @history:
     */
    public int update(JewelRecordNumber data);

    /**
     * 查询某个夺宝已分配抽奖号码
     * @param jewelCode
     * @return 
     * @create: 2017年1月9日 下午3:55:40 haiqingzheng
     * @history:
     */
    public List<String> selectExistNumbers(String jewelCode);
}
