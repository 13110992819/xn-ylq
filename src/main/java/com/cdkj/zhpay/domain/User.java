/**
 * @Title User.java 
 * @Package com.ibis.pz.domain 
 * @Description 
 * @author miyb  
 * @date 2015-2-6 上午10:31:01 
 * @version V1.0   
 */
package com.cdkj.zhpay.domain;

import com.cdkj.zhpay.dao.base.ABaseDO;

/** 
 * @author: miyb 
 * @since: 2015-2-6 上午10:31:01 
 * @history:
 */
public class User extends ABaseDO {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 1975331351390818527L;

    // userId
    private String userId;

    // 登陆名
    private String loginName;

    // 昵称
    private String nickname;

    // 头像
    private String photo;

    // 手机号
    private String mobile;

    // 是否实名认证
    private String identityFlag;

    // 推荐人
    private String userReferee;

    // 用户扩展信息
    private UserExt userExt;

    public UserExt getUserExt() {
        return userExt;
    }

    public void setUserExt(UserExt userExt) {
        this.userExt = userExt;
    }

    public String getUserReferee() {
        return userReferee;
    }

    public void setUserReferee(String userReferee) {
        this.userReferee = userReferee;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentityFlag() {
        return identityFlag;
    }

    public void setIdentityFlag(String identityFlag) {
        this.identityFlag = identityFlag;
    }
}
