package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.dao.IHzbMgiftDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.HzbMgift;

/**
 * 定向红包
 * @author: xieyj 
 * @since: 2017年2月20日 下午1:46:39 
 * @history:
 */
@Repository("hzbMgiftDAOImpl")
public class HzbMgiftDAOImpl extends AMybatisTemplate implements IHzbMgiftDAO {

    @Override
    public int insert(HzbMgift data) {
        return super.insert(NAMESPACE.concat("insert_hzbMgift"), data);
    }

    @Override
    public int delete(HzbMgift data) {
        return 0;
    }

    @Override
    public HzbMgift select(HzbMgift condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.select(NAMESPACE.concat("select_hzbMgift"), condition,
            HzbMgift.class);
    }

    @Override
    public Long selectTotalCount(HzbMgift condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectTotalCount(
            NAMESPACE.concat("select_hzbMgift_count"), condition);
    }

    @Override
    public List<HzbMgift> selectList(HzbMgift condition) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbMgift"), condition,
            HzbMgift.class);
    }

    @Override
    public List<HzbMgift> selectList(HzbMgift condition, int start, int count) {
        condition.setUserDB(PropertiesUtil.Config.USER_DB);
        return super.selectList(NAMESPACE.concat("select_hzbMgift"), start,
            count, condition, HzbMgift.class);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbMgiftDAO#updateStatus(com.cdkj.zhpay.domain.HzbMgift)
     */
    @Override
    public int updateStatus(HzbMgift data) {
        return super.update(NAMESPACE.concat("update_hzbMgift_status"), data);
    }

    /** 
     * @see com.cdkj.zhpay.dao.IHzbMgiftDAO#updateReciever(com.cdkj.zhpay.domain.HzbMgift)
     */
    @Override
    public int updateReciever(HzbMgift data) {
        return super.update(NAMESPACE.concat("update_hzbMgift_receiver"), data);
    }
}
