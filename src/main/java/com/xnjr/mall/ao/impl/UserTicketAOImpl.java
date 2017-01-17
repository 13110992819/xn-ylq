package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IUserTicketAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IStoreTicketBO;
import com.xnjr.mall.bo.IUserTicketBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StoreTicket;
import com.xnjr.mall.domain.UserTicket;
import com.xnjr.mall.dto.res.XN802503Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EStoreStatus;
import com.xnjr.mall.enums.EStoreTicketStatus;
import com.xnjr.mall.enums.ESysAccount;
import com.xnjr.mall.exception.BizException;

@Service
public class UserTicketAOImpl implements IUserTicketAO {

    @Autowired
    private IUserTicketBO userTicketBO;

    @Autowired
    private IStoreTicketBO storeTicketBO;

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private IAccountBO accountBO;

    @Override
    @Transactional
    public String buyTicket(String code, String userId) {
        StoreTicket storeTicket = storeTicketBO.getStoreTicket(code);
        Store store = storeBO.getStore(storeTicket.getStoreCode());
        // 判断店铺是否已开店
        if (!EStoreStatus.ONLINE_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "该店铺不处于开店状态");
        }
        if (!EStoreTicketStatus.ONLINE.getCode()
            .equals(storeTicket.getStatus())) {
            throw new BizException("xn0000", "折扣券不处于可购买状态");
        }
        UserTicket data = new UserTicket();
        data.setTicketCode(code);
        data.setUserId(userId);
        data.setSystemCode(storeTicket.getSystemCode());
        String ticketCode = userTicketBO.saveUserTicket(data);
        // 获取账户信息进行划账
        XN802503Res fromAccount = accountBO.getAccountByUserId(
            storeTicket.getSystemCode(), userId, ECurrency.QBB.getCode());
        String bizNote = fromAccount.getRealName() + "用户"
                + EBizType.AJ_GMZKQ.getValue();
        accountBO.doTransferAmount(store.getSystemCode(),
            fromAccount.getAccountNumber(), ESysAccount.QBB.getCode(),
            storeTicket.getPrice(), EBizType.AJ_GMZKQ.getCode(), bizNote);
        return ticketCode;
    }

    @Override
    public int dropUserTicket(String code) {
        if (!userTicketBO.isUserTicketExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return userTicketBO.removeUserTicket(code);
    }

    @Override
    public Paginable<UserTicket> queryUserTicketPage(int start, int limit,
            UserTicket condition) {
        Paginable<UserTicket> page = userTicketBO.getPaginable(start, limit,
            condition);
        List<UserTicket> list = page.getList();
        for (UserTicket userTicket : list) {
            StoreTicket storeTicket = storeTicketBO.getStoreTicket(userTicket
                .getTicketCode());
            userTicket.setStoreTicket(storeTicket);
        }
        return page;
    }

    @Override
    public List<UserTicket> queryUserTicketList(UserTicket condition) {
        return userTicketBO.queryUserTicketList(condition);
    }

    @Override
    public UserTicket getUserTicket(String code) {
        return userTicketBO.getUserTicket(code);
    }
}
