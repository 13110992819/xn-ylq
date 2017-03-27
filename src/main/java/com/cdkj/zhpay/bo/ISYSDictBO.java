/**
 * @Title ISYSDictBO.java 
 * @Package com.xnjr.moom.bo 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午2:40:19 
 * @version V1.0   
 */
package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.SYSDict;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午2:40:19 
 * @history:
 */
public interface ISYSDictBO extends IPaginableBO<SYSDict> {

    public void removeSYSDict(Long id);

    public void checkKeys(String parentKey, String key, String systemCode,
            String companyCode);

    public void refreshSYSDict(Long id, String value, String updater,
            String remark);

    public List<SYSDict> querySYSDictList(SYSDict condition);

    public SYSDict getSYSDict(Long id);

    public Long saveSecondDict(String parentKey, String key, String value,
            String updater, String remark, String systemCode);

}
