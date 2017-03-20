package com.cdkj.zhpay.ao;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.SYSConfig;

/**
 * @author: xieyj 
 * @since: 2017年3月20日 下午5:45:43 
 * @history:
 */
public interface ISYSConfigAO {

    String DEFAULT_ORDER_COLUMN = "id";

    public void editSYSConfig(Long id, String cvalue, String updater,
            String remark);

    public Paginable<SYSConfig> querySYSConfigPage(int start, int limit,
            SYSConfig condition);

    public SYSConfig getSYSConfig(Long id);

    public SYSConfig getSYSConfig(String key, String companyCode,
            String systemCode);

}
