/**
 * @Title XN808307Req.java 
 * @Package com.xnjr.mall.dto.req 
 * @Description 
 * @author haiqingzheng  
 * @date 2017年1月10日 下午8:10:58 
 * @version V1.0   
 */
package com.xnjr.mall.dto.req;

/** 
 * @author: haiqingzheng 
 * @since: 2017年1月10日 下午8:10:58 
 * @history:
 */
public class XN808307Req {

    // 夺宝记录编号
    public String jewelRecordCode;

    // 购买次数
    public String times;

    public String getJewelRecordCode() {
        return jewelRecordCode;
    }

    public void setJewelRecordCode(String jewelRecordCode) {
        this.jewelRecordCode = jewelRecordCode;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
