/**
 * @Title SYSDictBOImpl.java 
 * @Package com.xnjr.moom.bo.impl 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午2:50:06 
 * @version V1.0   
 */
package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.ISYSDictBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.ISYSDictDAO;
import com.cdkj.zhpay.domain.SYSDict;

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

    /** 
     * @see com.cdkj.zhpay.bo.ISYSDictBO#saveSYSDict(com.cdkj.zhpay.domain.SYSDict)
     */
    @Override
    public Long saveSYSDict(SYSDict data) {
        Long id = null;
        if (data != null) {
            sysDictDAO.insert(data);
            id = data.getId();
        }
        return id;
    }

    /** 
     * @see com.cdkj.zhpay.bo.ISYSDictBO#removeSYSDict(java.lang.Long)
     */
    @Override
    public int removeSYSDict(Long id) {
        int count = 0;
        if (id != null) {
            SYSDict data = new SYSDict();
            data.setId(id);
            count = sysDictDAO.delete(data);
        }
        return count;
    }

    /** 
     * @see com.cdkj.zhpay.bo.ISYSDictBO#refreshSYSDict(com.cdkj.zhpay.domain.SYSDict)
     */
    @Override
    public int refreshSYSDict(SYSDict data) {
        int count = 0;
        if (data != null) {
            count = sysDictDAO.update(data);
        }
        return count;
    }

    /** 
     * @see com.cdkj.zhpay.bo.ISYSDictBO#getSYSDict(java.lang.Long)
     */
    @Override
    public SYSDict getSYSDict(Long id) {
        SYSDict sysDict = null;
        if (id != null) {
            SYSDict data = new SYSDict();
            data.setId(id);
            sysDict = sysDictDAO.select(data);
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

}
