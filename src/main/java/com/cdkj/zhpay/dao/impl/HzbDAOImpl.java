package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.dao.IHzbDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.Hzb;

@Repository("hzbDAOImpl")
public class HzbDAOImpl extends AMybatisTemplate implements IHzbDAO {

    @Override
    public int insert(Hzb data) {
        return super.insert(NAMESPACE.concat("insert_hzb"), data);
    }

    @Override
    public int insertThirdPay(Hzb data) {
        return super.insert(NAMESPACE.concat("insert_hzb_thirdPay"), data);
    }

    @Override
    public int delete(Hzb data) {
        return 0;
    }

    @Override
    public int updatePayStatus(Hzb data) {
        return super.update(NAMESPACE.concat("update_payStatus"), data);
    }

    @Override
    public int updatePutStatus(Hzb data) {
        return super.update(NAMESPACE.concat("update_putStatus"), data);
    }

    @Override
    public Hzb select(Hzb condition) {
        return super.select(NAMESPACE.concat("select_hzb"), condition,
            Hzb.class);
    }

    @Override
    public Long selectTotalCount(Hzb condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_hzb_count"),
            condition);
    }

    @Override
    public List<Hzb> selectList(Hzb condition) {
        return super.selectList(NAMESPACE.concat("select_hzb"), condition,
            Hzb.class);
    }

    @Override
    public List<Hzb> selectList(Hzb condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_hzb"), start, count,
            condition, Hzb.class);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#selectDistanceList(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public List<Hzb> selectDistanceList(Hzb condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzb_distance"),
            condition, Hzb.class);
    }

    @Override
    public int updatePeriodRockNumZero() {
        return super.update(NAMESPACE.concat("update_periodRockNumZero"), null);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#updateYy(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public int updateYy(Hzb data) {
        return super.update(NAMESPACE.concat("update_yy"), data);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#updateYyAmount(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public int updateYyAmount(Hzb data) {
        return super.update(NAMESPACE.concat("update_yy_amount"), data);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#updateYyTimes(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public int updateYyTimes(Hzb data) {
        return super.update(NAMESPACE.concat("update_yy_times"), data);
    }
}
