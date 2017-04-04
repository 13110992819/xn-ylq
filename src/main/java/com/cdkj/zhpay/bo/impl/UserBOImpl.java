package com.cdkj.zhpay.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.req.XN001400Req;
import com.cdkj.zhpay.dto.req.XN001403Req;
import com.cdkj.zhpay.dto.res.XN001400Res;
import com.cdkj.zhpay.dto.res.XN001401Res;
import com.cdkj.zhpay.dto.res.XN001403Res;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.enums.EUserKind;
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
    static Logger logger = Logger.getLogger(UserBOImpl.class);

    @Override
    public User getRemoteUser(String userId) {
        XN001400Req req = new XN001400Req();
        req.setTokenId(userId);
        req.setUserId(userId);
        XN001400Res res = BizConnecter.getBizData("001400",
            JsonUtils.object2Json(req), XN001400Res.class);
        if (res == null) {
            throw new BizException("XN000000", "编号为" + userId + "的用户不存在");
        }
        User user = new User();
        user.setUserId(res.getUserId());
        user.setOpenId(res.getOpenId());
        user.setLoginName(res.getLoginName());
        user.setNickname(res.getNickname());
        user.setPhoto(res.getPhoto());
        user.setMobile(res.getMobile());
        user.setIdentityFlag(res.getIdentityFlag());
        user.setUserReferee(res.getUserReferee());
        user.setProvince(res.getProvince());
        user.setCity(res.getCity());
        user.setArea(res.getArea());
        return user;

    }

    @Override
    public List<User> queryRemoteUserList(String province, String city,
            String area, EUserKind kind) {
        List<User> result = new ArrayList<User>();
        XN001403Req req = new XN001403Req();
        req.setProvince(province);
        req.setCity(city);
        req.setArea(area);
        req.setKind(kind.getCode());
        req.setSystemCode(ESystemCode.ZHPAY.getCode());
        String jsonStr = BizConnecter.getBizData("001403",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN001403Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN001403Res>>() {
            }.getType());
        if (CollectionUtils.isNotEmpty(list)) {
            for (XN001403Res res : list) {
                User user = new User();
                user.setUserId(res.getUserId());
                user.setLoginName(res.getLoginName());
                user.setPhoto(res.getPhoto());
                user.setMobile(res.getMobile());
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public User getPartner(String province, String city, String area) {
        // 只有省 province，city,area=省
        // 有省市 area=市
        if (StringUtils.isBlank(city) && StringUtils.isBlank(area)) {
            city = province;
            area = province;
        } else if (StringUtils.isBlank(area)) {
            area = city;
        }
        XN001403Req req = new XN001403Req();
        req.setProvince(province);
        req.setCity(city);
        req.setArea(area);
        req.setKind(EUserKind.Partner.getCode());
        req.setSystemCode(ESystemCode.ZHPAY.getCode());
        XN001401Res result = null;
        String jsonStr = BizConnecter.getBizData("001403",
            JsonUtils.object2Json(req));
        Gson gson = new Gson();
        List<XN001401Res> list = gson.fromJson(jsonStr,
            new TypeToken<List<XN001401Res>>() {
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
