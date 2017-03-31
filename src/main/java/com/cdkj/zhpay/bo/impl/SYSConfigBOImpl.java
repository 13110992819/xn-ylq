package com.cdkj.zhpay.bo.impl;

import java.util.Date;
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
import com.cdkj.zhpay.exception.BizException;

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
    public int refreshSYSConfig(Long id, String cvalue, String updater,
            String remark) {
        SYSConfig data = new SYSConfig();
        data.setId(id);
        data.setCvalue(cvalue);

        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        return sysConfigDAO.updateValue(data);
    }

    @Override
    public SYSConfig getSYSConfig(Long id) {
        SYSConfig sysConfig = null;
        if (id > 0) {
            SYSConfig condition = new SYSConfig();
            condition.setId(id);
            sysConfig = sysConfigDAO.select(condition);
        }
        if (sysConfig == null) {
            throw new BizException("xn000000", "id记录不存在");
        }
        return sysConfig;
    }

    @Override
    public Map<String, String> getConfigsMap(String systemCode) {
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

    @Override
    public SYSConfig getSYSConfig(String key, String companyCode,
            String systemCode) {
        SYSConfig sysConfig = null;
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(systemCode)
                && StringUtils.isNotBlank(companyCode)) {
            SYSConfig condition = new SYSConfig();
            condition.setCkey(key);
            condition.setCompanyCode(companyCode);
            condition.setSystemCode(systemCode);
            List<SYSConfig> sysConfigList = sysConfigDAO.selectList(condition);
            if (CollectionUtils.isNotEmpty(sysConfigList)) {
                sysConfig = sysConfigList.get(0);
            }
        }
        return sysConfig;
    }

    @Override
    public SYSConfig getSYSConfig(String key, String systemCode) {
        return getSYSConfig(key, systemCode, systemCode);
    }

}
