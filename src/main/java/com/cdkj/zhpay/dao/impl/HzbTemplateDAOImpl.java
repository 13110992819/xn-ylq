package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.dao.IHzbTemplateDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.HzbTemplate;

@Repository("hzbTemplateDAOImpl")
public class HzbTemplateDAOImpl extends AMybatisTemplate implements
        IHzbTemplateDAO {

    @Override
    public int insert(HzbTemplate data) {
        return super.insert(NAMESPACE.concat("insert_hzbTemplate"), data);
    }

    @Override
    public int delete(HzbTemplate data) {
        return super.delete(NAMESPACE.concat("delete_hzbTemplate"), data);
    }

    @Override
    public int update(HzbTemplate data) {
        return super.update(NAMESPACE.concat("update_hzbTemplate"), data);
    }

    @Override
    public int putOnTemplate(HzbTemplate data) {
        return super.update(NAMESPACE.concat("update_hzbTemplate_putOn"), data);
    }

    @Override
    public int putOffTemplate(HzbTemplate data) {
        return super
            .update(NAMESPACE.concat("update_hzbTemplate_putOff"), data);
    }

    @Override
    public HzbTemplate select(HzbTemplate condition) {
        return super.select(NAMESPACE.concat("select_hzbTemplate"), condition,
            HzbTemplate.class);
    }

    @Override
    public Long selectTotalCount(HzbTemplate condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_hzbTemplate_count"), condition);
    }

    @Override
    public List<HzbTemplate> selectList(HzbTemplate condition) {
        return super.selectList(NAMESPACE.concat("select_hzbTemplate"),
            condition, HzbTemplate.class);
    }

    @Override
    public List<HzbTemplate> selectList(HzbTemplate condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_hzbTemplate"), start,
            count, condition, HzbTemplate.class);
    }
}
