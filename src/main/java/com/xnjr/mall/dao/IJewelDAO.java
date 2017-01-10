package com.xnjr.mall.dao;

import com.xnjr.mall.dao.base.IBaseDAO;
import com.xnjr.mall.domain.Jewel;

/**
 * @author: shan 
 * @since: 2016年12月19日 下午3:18:16 
 * @history:
 */
public interface IJewelDAO extends IBaseDAO<Jewel> {
    String NAMESPACE = IJewelDAO.class.getName().concat(".");

    /**
     * @param data
     * @return 
     * @create: 2016年12月19日 下午3:20:16 shan
     * @history:
     */
    public int update(Jewel data);

    public int updateStatus(Jewel data);

    public int updateInvestInfo(Jewel data);

    public int updateWinInfo(Jewel data);
}
