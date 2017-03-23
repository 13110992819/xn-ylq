package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelRecordNumber;

public interface IJewelRecordNumberBO extends IPaginableBO<JewelRecordNumber> {
    public void saveJewelRecordNumber(String jewelRecordCode, Jewel jewel,
            Integer times);

    /**
     * 
     * @param data
     * @return 
     * @create: 2016年12月20日 下午12:49:38 shan
     * @history:
     */
    public List<JewelRecordNumber> queryJewelRecordNumberList(
            String jewelRecordCode);

    public List<JewelRecordNumber> queryJewelRecordNumberList(
            JewelRecordNumber data);

    public List<String> queryExistNumbers(String jewelCode);

    public Long getJewelNumberTotalCount(String userId, String jewelCode);

}
