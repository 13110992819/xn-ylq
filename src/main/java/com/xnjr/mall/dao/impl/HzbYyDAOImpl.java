package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.IHzbYyDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.HzbYy;

@Repository("hzbYyDAOImpl")
public class HzbYyDAOImpl extends AMybatisTemplate implements IHzbYyDAO {

    @Override
    public int insert(HzbYy data) {
        return super.insert(NAMESPACE.concat("insert_hzbYy"), data);
    }

    @Override
    public int delete(HzbYy data) {
        return super.delete(NAMESPACE.concat("delete_hzbYy"), data);
    }

    @Override
    public HzbYy select(HzbYy condition) {
        return super.select(NAMESPACE.concat("select_hzbYy"), condition,
            HzbYy.class);
    }

    @Override
    public Long selectTotalCount(HzbYy condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_hzbYy_count"),
            condition);
    }

    @Override
    public List<HzbYy> selectList(HzbYy condition) {
        return super.selectList(NAMESPACE.concat("select_hzbYy"), condition,
            HzbYy.class);
    }

    @Override
    public List<HzbYy> selectList(HzbYy condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_hzbYy"), start, count,
            condition, HzbYy.class);
    }

}
