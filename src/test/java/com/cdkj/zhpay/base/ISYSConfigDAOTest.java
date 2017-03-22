package com.cdkj.zhpay.base;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.cdkj.zhpay.dao.ISYSConfigDAO;
import com.cdkj.zhpay.domain.SYSConfig;

public class ISYSConfigDAOTest extends ADAOTest {

    @SpringBeanByType
    private ISYSConfigDAO sysConfigDAO;

    @Test
    public void insert() {
        SYSConfig data = new SYSConfig();
        data.setType("1");
        data.setCkey("Ckey");
        data.setCvalue("Cvalue");

        data.setUpdater("updater");
        data.setUpdateDatetime(new Date());
        data.setRemark("remark");
        data.setSystemCode("systemCode");
        data.setCompanyCode("companyCode");
        sysConfigDAO.insert(data);
        logger.info("insert : {}", data.getId());

    }

    @Test
    public void select() {
        SYSConfig condition = new SYSConfig();
        condition.setId(1L);
        SYSConfig data = sysConfigDAO.select(condition);
        logger.info("select : {}", data.getId());
    }

}
