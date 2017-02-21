package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.req.XN805042Req;
import com.cdkj.zhpay.dto.req.XN805060Req;
import com.cdkj.zhpay.dto.req.XN805300Req;
import com.cdkj.zhpay.dto.req.XN805901Req;
import com.cdkj.zhpay.dto.req.XN805902Req;
import com.cdkj.zhpay.dto.req.XN805910Req;
import com.cdkj.zhpay.dto.res.XN805042Res;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.dto.res.XN805910Res;
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

    /** 
     * @see com.cdkj.zhpay.bo.IUserBO#getRemoteUser(java.lang.String)
     */
    @Override
    public XN805901Res getRemoteUser(String tokenId, String userId) {
        XN805901Req req = new XN805901Req();
        req.setTokenId(tokenId);
        req.setUserId(userId);
        XN805901Res res = BizConnecter.getBizData("805901",
            JsonUtils.object2Json(req), XN805901Res.class);
        if (res == null) {
            throw new BizException("XN000000", "编号为" + userId + "的用户不存在");
        }
        return res;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IUserBO#checkTradePwd(java.lang.String, java.lang.String)
     */
    @Override
    public void checkTradePwd(String userId, String tradePwd) {
        XN805902Req req = new XN805902Req();
        req.setUserId(userId);
        req.setTradePwd(tradePwd);
        BizConnecter.getBizData("805902", JsonUtils.object2Json(req),
            Object.class);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IUserBO#doTransfer(java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
     */
    @Override
    public void doTransfer(String userId, String direction, Long amount,
            String remark, String refNo) {
        XN805300Req req = new XN805300Req();
        req.setUserId(userId);
        req.setDirection(direction);
        req.setAmount(String.valueOf(amount));
        req.setRemark(remark);
        req.setRefNo(refNo);
        BizConnecter.getBizData("805300", JsonUtils.object2Json(req),
            Object.class);
    }

    /** 
     * @see com.cdkj.zhpay.bo.IUserBO#doSaveUser(com.cdkj.zhpay.dto.req.XN805042Req)
     */
    @Override
    public String doSaveUser(XN805042Req req) {
        XN805042Res res = BizConnecter.getBizData("805042",
            JsonUtils.object2Json(req), XN805042Res.class);
        return res.getUserId();
    }

    @Override
    public String getUserId(String mobile, String kind, String systemCode) {
        String userId = null;
        XN805910Req req = new XN805910Req();
        req.setMobile(mobile);
        req.setKind(kind);
        req.setSystemCode(systemCode);
        XN805910Res res = BizConnecter.getBizData("805910",
            JsonUtils.object2Json(req), XN805910Res.class);
        if (res != null) {
            userId = res.getUserId();
        }
        return userId;
    }

    /** 
     * @see com.cdkj.zhpay.bo.IUserBO#getPartnerUserInfo(com.cdkj.zhpay.dto.req.XN805060Req)
     */
    @Override
    public XN805060Res getPartnerUserInfo(String province, String city,
            String area) {
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
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
        }
        return result;
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
    public List<XN805060Res> getUserList(String province, String city,
            String area, String kind) {
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
}