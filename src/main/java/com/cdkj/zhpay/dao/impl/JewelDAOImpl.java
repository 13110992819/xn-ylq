package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.dao.IJewelDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.Jewel;

/**
 * 
 * @author: shan 
 * @since: 2016年12月19日 下午3:22:11 
 * @history:
 */
@Repository("jewelDAOImpl")
public class JewelDAOImpl extends AMybatisTemplate implements IJewelDAO {

    @Override
    public int insert(Jewel data) {
        return super.insert(NAMESPACE.concat("insert_jewel"), data);
    }

    @Override
    public int delete(Jewel data) {
        return super.delete(NAMESPACE.concat("delete_jewel"), data);
    }

    @Override
    public Jewel select(Jewel condition) {
        return super.select(NAMESPACE.concat("select_jewel"), condition,
            Jewel.class);
    }

    @Override
    public Long selectTotalCount(Jewel condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_jewel_count"),
            condition);
    }

    @Override
    public List<Jewel> selectList(Jewel condition) {
        return super.selectList(NAMESPACE.concat("select_jewel"), condition,
            Jewel.class);
    }

    @Override
    public List<Jewel> selectList(Jewel condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_jewel"), start, count,
            condition, Jewel.class);
    }

    @Override
    public int updateStatus(Jewel data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

    @Override
    public int updateInvestInfo(Jewel data) {
        return super.update(NAMESPACE.concat("update_investInfo"), data);
    }

    @Override
    public int updateWinInfo(Jewel data) {
        return super.update(NAMESPACE.concat("update_winInfo"), data);
    }
}
