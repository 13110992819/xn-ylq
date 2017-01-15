package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.common.PropertiesUtil;
import com.xnjr.mall.dao.IJewelRecordDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.JewelRecord;

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
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.select(NAMESPACE.concat("select_jewelRecord"), condition,
            JewelRecord.class);
    }

    @Override
    public Long selectTotalCount(JewelRecord condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectTotalCount(
            NAMESPACE.concat("select_jewelRecord_count"), condition);
    }

    @Override
    public List<JewelRecord> selectList(JewelRecord condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_jewelRecord"),
            condition, JewelRecord.class);
    }

    @Override
    public List<JewelRecord> selectList(JewelRecord condition, int start,
            int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_jewelRecord"), start,
            count, condition, JewelRecord.class);
    }

    @Override
    public int update(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_jewelRecord"), data);
    }

    @Override
    public int updateLostInfo(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_lostInfo"), data);
    }

    @Override
    public int updateReAddress(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_jewelRecordReAddress"),
            data);
    }

    @Override
    public int updatePayAmount(JewelRecord data) {
        return super.update(NAMESPACE.concat("update_jewelRecordPayAmount"),
            data);
    }

    @Override
    public JewelRecord selectMy(JewelRecord condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.select(NAMESPACE.concat("select_my_jewelRecord"),
            condition, JewelRecord.class);
    }

    @Override
    public List<JewelRecord> selectMyList(JewelRecord condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_my_jewelRecord"),
            condition, JewelRecord.class);
    }

    @Override
    public List<JewelRecord> selectMyList(JewelRecord condition, int start,
            int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_my_jewelRecord"),
            start, count, condition, JewelRecord.class);
    }
}
