package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.dao.IHzbHoldDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.HzbHold;

@Repository("hzbHoldDAOImpl")
public class HzbHoldDAOImpl extends AMybatisTemplate implements IHzbHoldDAO {

    @Override
    public int insert(HzbHold data) {
        return super.insert(NAMESPACE.concat("insert_hzbHold"), data);
    }

    @Override
    public int delete(HzbHold data) {
        return super.delete(NAMESPACE.concat("delete_hzbHold"), data);
    }

    @Override
    public HzbHold select(HzbHold condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.select(NAMESPACE.concat("select_hzbHold"), condition,
            HzbHold.class);
    }

    @Override
    public Long selectTotalCount(HzbHold condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectTotalCount(NAMESPACE.concat("select_hzbHold_count"),
            condition);
    }

    @Override
    public List<HzbHold> selectList(HzbHold condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold"), condition,
            HzbHold.class);
    }

    @Override
    public List<HzbHold> selectList(HzbHold condition, int start, int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold"), start,
            count, condition, HzbHold.class);
    }

    @Override
    public int updateStatus(HzbHold data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbHoldDAO#selectDistanceTotalCount(com.cdkj.zhpay.domain.HzbHold)
     */
    @Override
    public Long selectDistanceTotalCount(HzbHold condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectTotalCount(
            NAMESPACE.concat("select_hzbHold_distance_count"), condition);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbHoldDAO#selectDistanceList(com.cdkj.zhpay.domain.HzbHold)
     */
    @Override
    public List<HzbHold> selectDistanceList(HzbHold condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold_distance"),
            condition, HzbHold.class);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbHoldDAO#selectDistanceList(com.cdkj.zhpay.domain.HzbHold, int, int)
     */
    @Override
    public List<HzbHold> selectDistanceList(HzbHold condition, int start,
            int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbHold_distance"),
            start, count, condition, HzbHold.class);
    }

    @Override
    public int updateRockNum(HzbHold data) {
        return super.update(NAMESPACE.concat("update_rockNum"), data);
    }

    @Override
    public int resetPeriodRockNum() {
        return super
            .update(NAMESPACE.concat("update_resetPeriodRockNum"), null);
    }

    @Override
    public Long getTotalAmount(HzbHold condition) {
        return super.select(NAMESPACE.concat("select_totalAmount"), condition,
            Long.class);
    }
}
