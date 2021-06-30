package com.example.simple.service.impl;

import com.example.simple.model.Account;
import com.example.simple.model.User;
import com.example.simple.service.AccountService;

import java.util.UUID;

/**
 * AccountServiceImpl
 *
 * @author Mrhan
 * @date 2021/6/30 17:32
 */
public class AccountServiceImpl implements AccountService {

    private int count = 0;

    public AccountServiceImpl() {

    }

    @Override
    public Account createAccount(User user) {
        Account account = new Account();
        account.setBalance(0);
        account.setUsername(user.getUsername());
        account.setType((count++) % 2 == 0 ? "微信" : "支付宝");
        account.setNumber(UUID.randomUUID().toString());
        System.out.println("账户服务被调用-" + count);
        return account;
    }
}
