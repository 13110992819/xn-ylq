package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN805060Res;

/**
 * 走协议的带Remote
 * @author: xieyj 
 * @since: 2016年5月30日 上午9:28:13 
 * @history:
 */
public interface IUserBO {

    /**
     * 获取用户信息
     * @param userId
     * @return 
     * @create: 2017年3月22日 下午3:50:24 myb858
     * @history:
     */
    public User getRemoteUser(String userId);

    /**
     * 获取用户列表
     * @param province
     * @param city
     * @param area
     * @param kind
     * @return 
     * @create: 2017年1月15日 下午5:56:30 xieyj
     * @history:
     */
    public List<XN805060Res> queryRemoteUserList(String province, String city,
            String area);

    /**
     * 根据systemCode获取系统userId
     * @param systemCode
     * @return 
     * @create: 2017年3月23日 下午2:03:03 myb858
     * @history:
     */
    public String getSystemUser(String systemCode);
}
