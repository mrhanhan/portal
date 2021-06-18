package com.example.simple.service.impl;

import com.example.simple.model.User;
import com.example.simple.service.UserService;

/**
 * UserServiceImpl
 *
 * @author Mrhan
 * @date 2021/6/17 19:02
 */
public class UserServiceImpl implements UserService {
    @Override
    public User login(User user) {
        user.setMoney(1000000);
        user.setOrder(1000000);
        user.setRole("超管");
        user.setAccount("微信账户");
        return user;
    }
}
