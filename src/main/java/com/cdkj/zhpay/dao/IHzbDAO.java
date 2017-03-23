package com.cdkj.zhpay.dao;

import java.util.List;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.Hzb;

public interface IHzbDAO extends IBaseDAO<Hzb> {
    String NAMESPACE = IHzbDAO.class.getName().concat(".");

    public int insertThirdPay(Hzb data);

    public int updatePayStatus(Hzb data);

    public int updatePutStatus(Hzb data);

    public int updatePeriodRockNumZero();

    public List<Hzb> selectDistanceList(Hzb condition);

    public int updateYy(Hzb data);

}
