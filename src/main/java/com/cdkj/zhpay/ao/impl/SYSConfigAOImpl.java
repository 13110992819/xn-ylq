package com.cdkj.zhpay.ao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.ISYSConfigAO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.SYSConfig;
import com.cdkj.zhpay.exception.BizException;

/**
 * @author: Gejin 
 * @since: 2016年4月17日 下午7:32:28 
 * @history:
 */
@Service
public class SYSConfigAOImpl implements ISYSConfigAO {
    @Autowired
    ISYSConfigBO sysConfigBO;

    @Override
    public Long addSYSConfig(SYSConfig data) {
        Long id = null;
        if (data != null) {
            if (StringUtils.isNotBlank(data.getCkey())) {
                SYSConfig condition = new SYSConfig();
                condition.setCkey(data.getCkey());
                if (sysConfigBO.getTotalCount(condition) > 0) {
                    throw new BizException("lh5030", "ckey不能重复");
                }
                sysConfigBO.saveSYSConfig(data);
                id = data.getId();
            }
        }
        return id;
    }

    @Override
    public void editSYSConfig(SYSConfig data) {
        SYSConfig sysConfig = sysConfigBO.getConfig(data.getId());
        if (0L == sysConfig.getBelong()
                && StringUtils.isNotBlank(data.getCompanyCode())) {
            data.setCname(sysConfig.getCname());
            data.setCkey(sysConfig.getCkey());
            data.setBelong(data.getId());
            data.setSystemCode(sysConfig.getSystemCode());
            sysConfigBO.saveSYSConfig(data);
        } else {
            sysConfigBO.refreshSYSConfig(data);
        }
    }

    @Override
    public Paginable<SYSConfig> querySYSConfigPage(int start, int limit,
            SYSConfig condition) {
        return sysConfigBO.getPaginable(start, limit, condition);
    }

    @Override
    public SYSConfig getSYSConfig(Long id) {
        return sysConfigBO.getConfig(id);
    }

    @Override
    public String getConfigValue(String systemCode, String type,
            String companyCode, String key) {
        return sysConfigBO.getConfigValue(systemCode, type, companyCode, key);
    }
}
