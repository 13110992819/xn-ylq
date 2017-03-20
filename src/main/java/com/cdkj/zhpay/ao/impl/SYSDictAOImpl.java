/**
 * @Title SYSDictAOImpl.java 
 * @Package com.xnjr.moom.ao.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午5:19:00 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.ISYSDictAO;
import com.cdkj.zhpay.bo.ISYSDictBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.SYSDict;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午5:19:00 
 * @history:
 */
@Service
public class SYSDictAOImpl implements ISYSDictAO {
    @Autowired
    ISYSDictBO sysDictBO;

    @Override
    public Long addSYSDict(String parentKey, String key, String value,
            String updater, String remark) {
        sysDictBO.checkKeys(parentKey, key);
        return sysDictBO.saveSecondDict(parentKey, key, value, updater, remark);
    }

    @Override
    public void dropSYSDict(Long id) {
        sysDictBO.removeSYSDict(id);
    }

    @Override
    public void editSYSDict(Long id, String value, String updater, String remark) {
        sysDictBO.refreshSYSDict(id, value, updater, remark);
    }

    @Override
    public Paginable<SYSDict> querySYSDictPage(int start, int limit,
            SYSDict condition) {
        return sysDictBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<SYSDict> querySysDictList(SYSDict condition) {
        return sysDictBO.querySYSDictList(condition);
    }

    @Override
    public SYSDict getSYSDict(Long id) {

        return sysDictBO.getSYSDict(id);

    }
}
