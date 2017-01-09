package com.xnjr.mall.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xnjr.mall.bo.IHzbHoldBO;
import com.xnjr.mall.bo.base.Page;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.bo.base.PaginableBOImpl;
import com.xnjr.mall.dao.IHzbHoldDAO;
import com.xnjr.mall.domain.HzbHold;
import com.xnjr.mall.exception.BizException;

@Component
public class HzbHoldBOImpl extends PaginableBOImpl<HzbHold> implements
        IHzbHoldBO {

    @Autowired
    private IHzbHoldDAO hzbHoldDAO;

    @Override
    public boolean isHzbHoldExist(Long id) {
        HzbHold condition = new HzbHold();
        condition.setId(id);
        if (hzbHoldDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int saveHzbHold(HzbHold data) {
        int count = 0;
        if (data != null) {
            count = hzbHoldDAO.insert(data);
        }
        return count;
    }

    @Override
    public int removeHzbHold(Long id) {
        int count = 0;
        if (id != null) {
            HzbHold data = new HzbHold();
            data.setId(id);
            count = hzbHoldDAO.delete(data);
        }
        return count;
    }

    @Override
    public List<HzbHold> queryHzbHoldList(HzbHold condition) {
        return hzbHoldDAO.selectList(condition);
    }

    @Override
    public HzbHold getHzbHold(Long id) {
        HzbHold data = null;
        if (id != null) {
            HzbHold condition = new HzbHold();
            condition.setId(id);
            data = hzbHoldDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "汇赚宝购买记录不存在");
            }
        }
        return data;
    }

    @Override
    public int refreshStatus(Long id, String status) {
        int count = 0;
        if (id != null) {
            HzbHold data = new HzbHold();
            data.setId(id);
            data.setStatus(status);
            count = hzbHoldDAO.updateStatus(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IHzbHoldBO#queryDistanceHzbHoldList(com.xnjr.mall.domain.HzbHold)
     */
    @Override
    public List<HzbHold> queryDistanceHzbHoldList(HzbHold condition) {
        return hzbHoldDAO.selectDistanceList(condition);
    }

    @Override
    public Paginable<HzbHold> queryDistancePaginable(int start, int pageSize,
            HzbHold condition) {
        long totalCount = hzbHoldDAO.selectDistanceTotalCount(condition);
        Paginable<HzbHold> page = new Page<HzbHold>(start, pageSize, totalCount);
        List<HzbHold> dataList = hzbHoldDAO.selectDistanceList(condition,
            page.getStart(), page.getPageSize());
        page.setList(dataList);
        return page;
    }
}
