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
    public void removeSYSDict(Long id) {
        if (id > 0) {
            SYSDict data = new SYSDict();
            data.setId(id);
            sysDictDAO.delete(data);
        }
    }

    @Override
    public void refreshSYSDict(Long id, String value, String updater,
            String remark) {
        SYSDict data = new SYSDict();
        data.setId(id);
        data.setDvalue(value);

        data.setUpdater(updater);
        data.setUpdateDatetime(new Date());
        data.setRemark(remark);
        sysDictDAO.update(data);

    }

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

    @Override
    public List<SYSDict> querySYSDictList(SYSDict condition) {
        return sysDictDAO.selectList(condition);
    }

    @Override
    public Long saveSecondDict(SYSDict sysDict) {
        Long id = null;
        if (sysDict != null) {
            sysDictDAO.insert(sysDict);
            id = sysDict.getId();
        }
        return id;
    }

    @Override
    public void checkKeys(String parentKey, String key, String systemCode,
            String companyCode) {
        // 查看父节点是否存在
        SYSDict fDict = new SYSDict();
        fDict.setDkey(parentKey);
        fDict.setType(EDictType.FIRST.getCode());
        fDict.setSystemCode(systemCode);
        fDict.setCompanyCode(companyCode);
        if (getTotalCount(fDict) <= 0) {
            throw new BizException("xn000000", "parentKey不存在");
        }
        // 第二层数据字典 在当前父节点下key不能重复
        SYSDict condition = new SYSDict();
        condition.setParentKey(parentKey);
        condition.setDkey(key);
        condition.setType(EDictType.SECOND.getCode());
        condition.setSystemCode(systemCode);
        condition.setCompanyCode(companyCode);
        if (getTotalCount(condition) > 0) {
            throw new BizException("xn000000", "当前节点下，key重复");
        }

    }

}
