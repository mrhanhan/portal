package com.example.simple.service;

import com.example.simple.model.Account;
import com.example.simple.model.User;

/**
 * AccountService
 *
 * @author Mrhan
 * @date 2021/6/30 17:31
 */
public interface AccountService {
    /**
     * 为用户创建账号
     * @param user 用户信息
     * @return 返回用户关联的账户信息
     */
    Account createAccount(User user);
}
