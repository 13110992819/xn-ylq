/**
 * @Title SYSDictBOImpl.java 
 * @Package com.xnjr.moom.bo.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午2:50:06 
 * @version V1.0   
 */
package com.cdkj.zhpay.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.ISYSDictBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.ISYSDictDAO;
import com.cdkj.zhpay.domain.SYSDict;
import com.cdkj.zhpay.enums.EDictType;
import com.cdkj.zhpay.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午2:50:06 
 * @history:
 */
@Component
public class SYSDictBOImpl extends PaginableBOImpl<SYSDict> implements
        ISYSDictBO {
    @Autowired
    private ISYSDictDAO sysDictDAO;

    @Override
    public int removeSYSDict(Long id) {
        int count = 0;
        if (id > 0) {
            SYSDict data = new SYSDict();
            data.setId(id);
            count = sysDictDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshSYSDict(Long id, String value, String updater,
            String remark) {
        SYSDict data = new SYSDict();
        data.setId(id);
        data.setDvalue(value);

        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        return sysDictDAO.update(data);
    }

    /** 
     * @see com.cdkj.zhpay.bo.ISYSDictBO#getSYSDict(java.lang.Long)
     */
    @Override
    public SYSDict getSYSDict(Long id) {
        SYSDict sysDict = null;
        if (id > 0) {
            SYSDict data = new SYSDict();
            data.setId(id);
            sysDict = sysDictDAO.select(data);
        }
        if (sysDict == null) {
            throw new BizException("xn000000", "id记录不存在");
        }
        return sysDict;

    }

    /** 
     * @see com.cdkj.zhpay.bo.ISYSDictBO#querySYSDictList(com.cdkj.zhpay.domain.SYSDict)
     */
    @Override
    public List<SYSDict> querySYSDictList(SYSDict condition) {
        return sysDictDAO.selectList(condition);
    }

    @Override
    public Long saveSecondDict(String parentKey, String key, String value,
            String updater, String remark, String systemCode) {
        SYSDict sysDict = new SYSDict();
        sysDict.setType(EDictType.SECOND.getCode());
        sysDict.setParentKey(parentKey);
        sysDict.setDkey(key);
        sysDict.setDvalue(value);

        sysDict.setUpdater(updater);
        sysDict.setUpdateDatetime(new Date());
        sysDict.setRemark(remark);
        sysDict.setSystemCode(systemCode);
        sysDictDAO.insert(sysDict);
        return sysDict.getId();

    }

    @Override
    public void checkKeys(String parentKey, String key, String systemCode) {
        // 查看父节点是否存在
        SYSDict fDict = new SYSDict();
        fDict.setDkey(parentKey);
        fDict.setType(EDictType.FIRST.getCode());
        fDict.setSystemCode(systemCode);
        if (getTotalCount(fDict) <= 0) {
            throw new BizException("xn000000", "parentKey不存在");
        }
        // 第二层数据字典 在当前父节点下key不能重复
        SYSDict condition = new SYSDict();
        condition.setParentKey(parentKey);
        condition.setDkey(key);
        condition.setType(EDictType.SECOND.getCode());
        condition.setSystemCode(systemCode);
        if (getTotalCount(condition) > 0) {
            throw new BizException("xn000000", "当前节点下，key重复");
        }
    }

}
