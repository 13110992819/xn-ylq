package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.dao.IHzbDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.Hzb;

@Repository("hzbHoldDAOImpl")
public class HzbDAOImpl extends AMybatisTemplate implements IHzbDAO {

    @Override
    public int insert(Hzb data) {
        return super.insert(NAMESPACE.concat("insert_hzbHold"), data);
    }

    @Override
    public int delete(Hzb data) {
        return super.delete(NAMESPACE.concat("delete_hzbHold"), data);
    }

    @Override
    public Hzb select(Hzb condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.select(NAMESPACE.concat("select_hzbHold"), condition,
            Hzb.class);
    }

    @Override
    public Long selectTotalCount(Hzb condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectTotalCount(NAMESPACE.concat("select_hzbHold_count"),
            condition);
    }

    @Override
    public List<Hzb> selectList(Hzb condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold"), condition,
            Hzb.class);
    }

    @Override
    public List<Hzb> selectList(Hzb condition, int start, int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold"), start,
            count, condition, Hzb.class);
    }

    @Override
    public int updateStatus(Hzb data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public int updatePayStatus(Hzb data) {
        return super.update(NAMESPACE.concat("update_pay_status"), data);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#selectDistanceTotalCount(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public Long selectDistanceTotalCount(Hzb condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectTotalCount(
            NAMESPACE.concat("select_hzbHold_distance_count"), condition);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#selectDistanceList(com.cdkj.zhpay.domain.Hzb)
     */
    @Override
    public List<Hzb> selectDistanceList(Hzb condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold_distance"),
            condition, Hzb.class);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbDAO#selectDistanceList(com.cdkj.zhpay.domain.Hzb, int, int)
     */
    @Override
    public List<Hzb> selectDistanceList(Hzb condition, int start,
            int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold_distance"),
            start, count, condition, Hzb.class);
    }

    @Override
    public int updateRockNum(Hzb data) {
        return super.update(NAMESPACE.concat("update_rockNum"), data);
    }

    @Override
    public int resetPeriodRockNum() {
        return super
            .update(NAMESPACE.concat("update_resetPeriodRockNum"), null);
    }

    @Override
    public Long getTotalAmount(Hzb condition) {
        return super.select(NAMESPACE.concat("select_totalAmount"), condition,
            Long.class);
    }
}
