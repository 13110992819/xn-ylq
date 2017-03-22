package com.cdkj.zhpay.base;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.cdkj.zhpay.dao.ISYSDictDAO;
import com.cdkj.zhpay.domain.SYSDict;

public class ISysDictDAOTest extends ADAOTest {

    @SpringBeanByType
    private ISYSDictDAO sysDictDAO;

    @Test
    public void select() {
        SYSDict condition = new SYSDict();
        condition.setId(1L);
        SYSDict data = sysDictDAO.select(condition);
        logger.info("select : {}", data.getId());
    }

}
