/**
 * @Title ISYSConfigBO.java 
 * @Package com.xnjr.moom.bo 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午2:40:52 
 * @version V1.0   
 */
package com.cdkj.zhpay.bo;

import java.util.Map;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.SYSConfig;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午2:40:52 
 * @history:
 */
public interface ISYSConfigBO extends IPaginableBO<SYSConfig> {

    public int refreshSYSConfig(Long id, String cvalue, String updater,
            String remark);

    public SYSConfig getSYSConfig(Long id);

    public Map<String, String> getConfigsMap(String systemCode,
            String companyCode);

    public SYSConfig getSYSConfig(String key, String companyCode,
            String systemCode);
}
