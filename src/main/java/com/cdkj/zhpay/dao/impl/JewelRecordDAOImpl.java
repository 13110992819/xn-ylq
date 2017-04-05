package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.dao.IJewelRecordDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;

/**
 * @author: xieyj 
 * @since: 2017年1月13日 下午2:17:34 
 * @history:
 */
@Repository("jewelRecordDAOImpl")
public class JewelRecordDAOImpl extends AMybatisTemplate implements
        IJewelRecordDAO {

    @Override
    public int insert(JewelRecord data) {
        return super.insert(NAMESPACE.concat("insert_jewelRecord"), data);
    }

    @Override
    public int delete(JewelRecord data) {
        return super.delete(NAMESPACE.concat("delete_jewelRecord"), data);
    }

    @Override
    public JewelRecord select(JewelRecord condition) {
        return super.select(NAMESPACE.concat("select_jewelRecord"), condition,
            JewelRecord.class);
    }

    @Override
    public Long selectTotalCount(JewelRecord condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_jewelRecord_count"), condition);
    }

    @Override
    public List<JewelRecord> selectList(JewelRecord condition) {
        return super.selectList(NAMESPACE.concat("select_jewelRecord"),
            condition, JewelRecord.class);
    }

    @Override
    public List<JewelRecord> selectList(JewelRecord condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_jewelRecord"), start,
            count, condition, JewelRecord.class);
    }

    @Override
    public int updateStatus(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public int updatePayStatus(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_pay_status"), data);
    }

    @Override
    public int updateLostInfo(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_lostInfo"), data);
    }

    @Override
    public List<Jewel> selectMyJewelList(JewelRecord condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_myjewel"), start,
            count, condition, Jewel.class);
    }

    @Override
    public Long selectMyJewelTotalCount(JewelRecord condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_myjewel_count"),
            condition);
    }

    @Override
    public Long getTotalAmount(JewelRecord condition) {
        return super.select(NAMESPACE.concat("select_totalAmount"), condition,
            Long.class);
    }
}
