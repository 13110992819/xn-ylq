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

    /**
     * systemCode=companyCode时调用此方法
     * @param systemCode
     * @return 
     * @create: 2017年3月22日 下午3:45:36 myb858
     * @history:
     */
    public Map<String, String> getConfigsMap(String systemCode);

    /**
     * systemCode=companyCode时调用此方法
     * @param key
     * @param systemCode
     * @return 
     * @create: 2017年3月22日 下午3:45:54 myb858
     * @history:
     */
    public SYSConfig getSYSConfig(String key, String systemCode);

}
