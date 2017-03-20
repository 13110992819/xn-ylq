package com.cdkj.zhpay.dao;

import java.util.List;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.Hzb;

public interface IHzbDAO extends IBaseDAO<Hzb> {
    String NAMESPACE = IHzbDAO.class.getName().concat(".");

    public int updateStatus(Hzb data);

    public int updatePayStatus(Hzb data);

    public int updateRockNum(Hzb data);

    public int resetPeriodRockNum();

    public Long selectDistanceTotalCount(Hzb condition);

    public List<Hzb> selectDistanceList(Hzb condition);

    public List<Hzb> selectDistanceList(Hzb condition, int start,
            int count);

    public Long getTotalAmount(Hzb condition);
}
