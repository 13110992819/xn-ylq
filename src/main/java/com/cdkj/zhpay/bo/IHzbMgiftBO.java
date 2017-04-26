package com.cdkj.zhpay.bo;

import java.util.Date;
import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.domain.User;

/**
 * @author: xieyj 
 * @since: 2017年2月20日 下午1:50:02 
 * @history:
 */
public interface IHzbMgiftBO extends IPaginableBO<HzbMgift> {

    /**
     * 验证当天是否已经产生过
     *  
     * @create: 2017年4月26日 下午7:03:21 xieyj
     * @history:
     */
    public void doCheckTodayGeneral();

    /**
     * 该时间前创建的红包制成“失效”
     * @param createDatetimeEnd 
     * @create: 2017年3月22日 下午4:11:26 myb858
     * @history:
     */
    public void doInvalidHzbMgift(Date createDatetimeEnd);

    /**
     * 形成当天摇钱树主人们的可发红包
     * @param hzb 摇钱树
     * @param createDatetime
     * @create: 2017年3月22日 下午4:27:12 myb858
     * @history:
     */
    public void generateHzbMgift(Hzb hzb, Date createDatetime);

    /**
     * 发送定向红包
     * @param hzbMgift 
     * @create: 2017年3月22日 下午4:04:43 myb858
     * @history:
     */
    public void doSendHzbMgift(HzbMgift hzbMgift);

    /**
     * 领取定向红包
     * @param hzbMgift
     * @param user
     * @return 
     * @create: 2017年3月22日 下午4:04:31 myb858
     * @history:
     */
    public void doReceiveHzbMgift(HzbMgift hzbMgift, User user);

    /**
     * 判断当前人员每天领取次数是否超限
     * @param userId
     * @param systemCode 
     * @create: 2017年4月21日 下午11:55:29 xieyj
     * @history:
     */
    public void checkMaxReceive(String userId, String systemCode);

    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition);

    public List<HzbMgift> queryReceiveHzbMgift(Date startDate, Date endDate,
            String userId);

    public Long getReceiveHzbMgiftCount(Date startDate, Date endDate,
            String userId);

    public HzbMgift getHzbMgift(String code);

}
