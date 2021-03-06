package com.cdkj.zhpay.bo;

import java.util.Date;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN615120Res;

public interface IHzbYyBO extends IPaginableBO<HzbYy> {

    /**
     * 检查所有摇一摇全局规则
     * @param hzb
     * @param yyUser
     * @param deviceNo 
     * @create: 2017年3月22日 下午7:08:09 myb858
     * @history:
     */
    public void checkYyGlobalRule(Hzb hzb, User yyUser, String deviceNo);

    public void checkYyGlobalRule(String systemCode, User yyUser,
            String deviceNo);

    /**
     * 记录摇一摇结果
     * @param prize
     * @param yyUser
     * @param hzb
     * @param deviceNo 
     * @create: 2017年3月22日 下午8:10:39 myb858
     * @history:
     */
    public String saveHzbYy(XN615120Res prize, User yyUser, Hzb hzb,
            String deviceNo, String ownerFcCurrency, Long ownerFcAmount);

    /**
     * 菜狗摇一摇算法
     * @return 
     * @create: 2017年3月22日 下午8:14:06 myb858
     * @history:
     */
    public XN615120Res calculatePrizeByCG(Hzb hzb);

    /**
     * 正汇钱包摇一摇算法
     * @param yyUser
     * @return 
     * @create: 2017年3月22日 下午8:29:44 myb858
     * @history:
     */

    public XN615120Res calculatePrizeByZH(Hzb hzb, User yyUser);

    /**
     * 获取某个时间段摇一摇次数
     * @param dateStart
     * @param dateEnd
     * @param code
     * @return 
     * @create: 2017年4月3日 下午2:52:47 xieyj
     * @history:
     */
    public Long getTotalHzbYyCount(Date dateStart, Date dateEnd, String hzbCode);

    /**
     * 获取树主人分成
     * @param dateStart
     * @param dateEnd
     * @param hzbCode
     * @return 
     * @create: 2017年4月3日 下午4:49:31 xieyj
     * @history:
     */
    public Long getTotalOwnerFcAmount(Date dateStart, Date dateEnd,
            String hzbCode);
}
