/**
 * @Title ISYSDictAO.java 
 * @Package com.xnjr.moom.ao 
 * @Description 
 * @author haiqingzheng  
 * @date 2016年4月17日 下午5:12:19 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.SYSDict;
import com.cdkj.zhpay.dto.req.XN615900Req;

/** 
 * @author: haiqingzheng 
 * @since: 2016年4月17日 下午5:12:19 
 * @history:
 */
public interface ISYSDictAO {
    static String DEFAULT_ORDER_COLUMN = "id";

    public Long addSecondDict(XN615900Req req);

    public void dropSYSDict(Long id);

    public void editSYSDict(Long id, String value, String updater, String remark);

    public Paginable<SYSDict> querySYSDictPage(int start, int limit,
            SYSDict condition);

    public List<SYSDict> querySysDictList(SYSDict condition);

    public SYSDict getSYSDict(Long id);

}
