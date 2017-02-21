package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.dto.req.XN805042Req;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;

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
     * @param tokenId
     * @param userId
     * @return 
     * @create: 2016年5月30日 下午3:00:44 xieyj
     * @history:
     */
    public XN805901Res getRemoteUser(String tokenId, String userId);

    /**
     * 校验支付密码
     * @param userId
     * @param tradePwd
     * @return 
     * @create: 2015年11月10日 上午9:16:42 myb858
     * @history:
     */
    public void checkTradePwd(String userId, String tradePwd);

    /**
     * 加减积分
     * @param userId
     * @param direction
     * @param amount
     * @param remark
     * @param refNo 
     * @create: 2016年10月12日 上午8:13:47 xieyj
     * @history:
     */
    public void doTransfer(String userId, String direction, Long amount,
            String remark, String refNo);

    /**
     *  新增用户
     * @param req
     * @return 
     * @create: 2016年7月26日 下午12:48:52 xieyj
     * @history:
     */
    public String doSaveUser(XN805042Req req);

    /**
     * 获取推荐人用户编号
     * @param mobile
     * @param kind
     * @param systemCode
     * @return 
     * @create: 2016年12月28日 上午10:09:53 xieyj
     * @history:
     */
    public String getUserId(String mobile, String kind, String systemCode);

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
