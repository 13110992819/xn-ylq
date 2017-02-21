package com.cdkj.zhpay.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.ISYSConfigDAO;
import com.cdkj.zhpay.domain.SYSConfig;

/**
 * 
 * @author: Gejin 
 * @since: 2016年4月17日 下午7:56:03 
 * @history:
 */

@Component
public class SYSConfigBOImpl extends PaginableBOImpl<SYSConfig> implements
        ISYSConfigBO {
    @Autowired
    private ISYSConfigDAO sysConfigDAO;

    @Override
    public boolean isSYSConfigExist(Long Id) {
        SYSConfig sysConfig = new SYSConfig();
        sysConfig.setId(Id);
        if (sysConfigDAO.selectTotalCount(sysConfig) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int saveSYSConfig(SYSConfig data) {
        int count = 0;
        if (data != null) {
            data.setId(data.getId());
            count = sysConfigDAO.insert(data);
        }
        return count;
    }

    @Override
    public int refreshSYSConfig(SYSConfig data) {
        int count = 0;
        if (data != null) {
            count = sysConfigDAO.updateValue(data);
        }
        return count;
    }

    @Override
    public SYSConfig getConfig(Long id) {
        SYSConfig sysConfig = null;
        if (id != null) {
            SYSConfig condition = new SYSConfig();
            condition.setId(id);
            sysConfig = sysConfigDAO.select(condition);
        }
        return sysConfig;
    }

    /** 
     * @see com.cdkj.zhpay.bo.ISYSConfigBO#getConfigValue(java.lang.String)
     */
    @Override
    public String getConfigValue(String systemCode, String type,
            String companyCode, String ckey) {
        String result = null;
        SYSConfig sysConfig = null;
        if (ckey != null) {
            SYSConfig condition = new SYSConfig();
            condition.setSystemCode(systemCode);
            condition.setType(type);
            condition.setCompanyCode(companyCode);
            condition.setCkey(ckey);
            sysConfig = sysConfigDAO.select(condition);
            if (sysConfig == null) {
                condition.setBelong(0L);
                condition.setCompanyCode(null);
                sysConfig = sysConfigDAO.select(condition);
            }
        }
        if (sysConfig != null) {
            result = sysConfig.getCvalue();
        }
        return result;
    }

    @Override
    public Map<String, String> getConfigsMap(String systemCode,
            String companyCode) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(systemCode)) {
            SYSConfig condition = new SYSConfig();
            condition.setSystemCode(systemCode);
            List<SYSConfig> list = sysConfigDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(list)) {
                for (SYSConfig sysConfig : list) {
                    map.put(sysConfig.getCkey(), sysConfig.getCvalue());
                }
            }
        }
        return map;
    }
}
