/**
 * @Title XN615020Res.java 
 * @Package com.cdkj.zhpay.dto.res 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年3月21日 上午11:47:02 
 * @version V1.0   
 */
package com.cdkj.zhpay.dto.res;

import java.util.List;

import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecord;
import com.cdkj.zhpay.domain.JewelRecordNumber;

/** 
 * @author: haiqingzheng 
 * @since: 2017年3月21日 上午11:47:02 
 * @history:
 */
public class XN615020Res {

    // 夺宝记录
    JewelRecord jewelRecord;

    // 夺宝记录关联宝贝信息
    Jewel jewel;

    // 夺宝人信息
    XN805901Res user;

    // 夺宝号码列表
    private List<JewelRecordNumber> jewelRecordNumberList;

    // 我投资人次
    private String myInvestTimes;

    public JewelRecord getJewelRecord() {
        return jewelRecord;
    }

    public void setJewelRecord(JewelRecord jewelRecord) {
        this.jewelRecord = jewelRecord;
    }

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }

    public XN805901Res getUser() {
        return user;
    }

    public void setUser(XN805901Res user) {
        this.user = user;
    }

    public List<JewelRecordNumber> getJewelRecordNumberList() {
        return jewelRecordNumberList;
    }

    public void setJewelRecordNumberList(
            List<JewelRecordNumber> jewelRecordNumberList) {
        this.jewelRecordNumberList = jewelRecordNumberList;
    }

    public String getMyInvestTimes() {
        return myInvestTimes;
    }

    public void setMyInvestTimes(String myInvestTimes) {
        this.myInvestTimes = myInvestTimes;
    }
}
