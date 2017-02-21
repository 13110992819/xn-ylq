package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Hzb;

public interface IHzbBO extends IPaginableBO<Hzb> {

    public boolean isHzbExist(String code);

    public int removeHzb(String code);

    public int refreshHzb(Hzb data);

    public List<Hzb> queryHzbList(Hzb condition);

    public Hzb getHzb(String code);

}
