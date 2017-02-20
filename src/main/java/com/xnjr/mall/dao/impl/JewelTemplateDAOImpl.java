package com.xnjr.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xnjr.mall.dao.IJewelTemplateDAO;
import com.xnjr.mall.dao.base.support.AMybatisTemplate;
import com.xnjr.mall.domain.JewelTemplate;

@Repository("JewelTemplateDAOImpl")
public class JewelTemplateDAOImpl extends AMybatisTemplate implements
        IJewelTemplateDAO {

    @Override
    public int insert(JewelTemplate data) {
        return super.insert(NAMESPACE.concat("insert_JewelTemplate"), data);
    }

    @Override
    public int delete(JewelTemplate data) {
        return super.delete(NAMESPACE.concat("delete_JewelTemplate"), data);
    }

    @Override
    public JewelTemplate select(JewelTemplate condition) {
        return super.select(NAMESPACE.concat("select_JewelTemplate"),
            condition, JewelTemplate.class);
    }

    @Override
    public Long selectTotalCount(JewelTemplate condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_JewelTemplate_count"), condition);
    }

    @Override
    public List<JewelTemplate> selectList(JewelTemplate condition) {
        return super.selectList(NAMESPACE.concat("select_JewelTemplate"),
            condition, JewelTemplate.class);
    }

    @Override
    public List<JewelTemplate> selectList(JewelTemplate condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_JewelTemplate"),
            start, count, condition, JewelTemplate.class);
    }

    @Override
    public int update(JewelTemplate data) {
        return super.update(NAMESPACE.concat("update_JewelTemplate"), data);
    }

}
