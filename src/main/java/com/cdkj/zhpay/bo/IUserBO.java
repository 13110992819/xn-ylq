package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN805060Res;

/**
 * @author: xieyj 
 * @since: 2016年5月30日 上午9:28:13 
 * @history:
 */
public interface IUserBO {

    /**
     * 验证用户
     * @param userId 
     * @create: 2017年2月21日 下午6:28:40 xieyj
     * @history:
     */
    public void doCheckUser(String userId);

    /**
     * 获取远程用户信息
     * @param userId
     * @return 
     * @create: 2017年3月22日 下午3:50:24 myb858
     * @history:
     */
    public User getRemoteUser(String userId);

    /**
     * 获取辖区合伙人详情
     * @param province
     * @param city
     * @param area
     * @return 
     * @create: 2016年12月29日 下午6:13:28 xieyj
     * @history:
     */
    public XN805060Res getPartnerUserInfo(String province, String city,
            String area);

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
    public List<XN805060Res> getUserList(String province, String city,
            String area, String kind);
}
