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

    public int saveSYSConfig(SYSConfig data);

    /**
     * 更新系统参数值
     * @param value 参数值
     * @param note 参数说明
     * @param updater 更新人
     * @param remark 备注
     * @return 
     * @create: 2016年4月17日 下午2:43:11 haiqingzheng
     * @history:
     */
    public int refreshSYSConfig(SYSConfig data);

    public SYSConfig getConfig(Long id);

    public Map<String, String> getConfigsMap(String systemCode,
            String companyCode);

    boolean isSYSConfigExist(Long Id);

    /**
     * 获取系统参数值
     * @param systemCode
     * @param type
     * @param companyCode
     * @param ckey
     * @return 
     * @create: 2016年12月27日 上午11:30:28 xieyj
     * @history:
     */
    public String getConfigValue(String systemCode, String type,
            String companyCode, String ckey);
}
