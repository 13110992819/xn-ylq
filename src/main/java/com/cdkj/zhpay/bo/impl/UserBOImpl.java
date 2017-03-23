package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.req.XN805060Req;
import com.cdkj.zhpay.dto.req.XN805901Req;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.enums.EUserKind;
import com.cdkj.zhpay.enums.EUserStatus;
import com.cdkj.zhpay.exception.BizException;
import com.cdkj.zhpay.http.BizConnecter;
import com.cdkj.zhpay.http.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author: xieyj 
 * @since: 2016年5月30日 上午9:28:30 
 * @history:
 */
@Component
public class UserBOImpl extends PaginableBOImpl<User> implements IUserBO {

    @Override
    public User getRemoteUser(String userId) {
        XN805901Req req = new XN805901Req();
        req.setTokenId(userId);
        req.setUserId(userId);
        XN805901Res res = BizConnecter.getBizData("805901",
            JsonUtils.object2Json(req), XN805901Res.class);
        if (res == null) {
            throw new BizException("XN000000", "编号为" + userId + "的用户不存在");
        }
        User user = new User();
        user.setUserId(userId);
        user.setLoginName(res.getLoginName());
        user.setNickname(res.getNickname());
        user.setPhoto(res.getPhoto());
        user.setMobile(res.getMobile());
        user.setIdentityFlag(res.getIdentityFlag());
        user.setUserReferee(res.getUserReferee());
        return user;
    }

    private User getPartnerUserInfo(String province, String city, String area) {
        // 只有省 province，city,area=省
        // 有省市 area=市
        if (StringUtils.isBlank(city) && StringUtils.isBlank(area)) {
            city = province;
            area = province;
        } else if (StringUtils.isBlank(area)) {
            area = city;
        }
        XN805060Req req = new XN805060Req();
        req.setProvince(province);
        req.setCity(city);
        req.setArea(area);
        req.setKind(EUserKind.Partner.getCode());
        req.setStatus(EUserStatus.NORMAL.getCode());
        XN805060Res result = null;
        String jsonStr = BizConnecter.getBizData("805060",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN805060Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN805060Res>>() {
            }.getType());
        User user = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
            user = new User();
            user.setUserId(result.getUserId());
            user.setLoginName(result.getLoginName());
            user.setMobile(result.getMobile());
        }
        return user;
    }

    /**
     * @param province
     * @param city
     * @param area
     * @param kind
     * @return 
     * @create: 2017年1月15日 下午5:56:14 xieyj
     * @history:
     */
    @Override
    public List<XN805060Res> queryRemoteUserList(String province, String city,
            String area) {
        XN805060Req req = new XN805060Req();
        req.setProvince(province);
        req.setCity(city);
        req.setArea(area);
        req.setKind(EUserKind.FCB.getCode());
        String jsonStr = BizConnecter.getBizData("805060",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN805060Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN805060Res>>() {
            }.getType());
        return list;
    }

    @Override
    public String getSystemUser(String systemCode) {
        if (ESystemCode.ZHPAY.getCode().equals(systemCode)) {
            return ESysUser.SYS_USER_ZHPAY.getCode();
        }
        if (ESystemCode.Caigo.getCode().equals(systemCode)) {
            return ESysUser.SYS_USER_CAIGO.getCode();
        }
        return null;
    }
}
