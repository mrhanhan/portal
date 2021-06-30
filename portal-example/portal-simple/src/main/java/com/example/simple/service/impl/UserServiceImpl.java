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

    private int count = 0;
    @Override
    public User login(User user) {
        user.setMoney(1000000);
        user.setOrder(1000000);
        user.setRole("超管");
        user.setAccount("微信账户");
        return user;
    }

    @Override
    public User getUserInfo(Long id) {
        User user = new User();
        user.setUsername("新增用户-" + (count ++ ));
        user.setPassword("123456");
        user.setMoney((int) System.currentTimeMillis());
        user.setOrder(Math.toIntExact(id));
        user.setRole("超管");
        user.setAccount("微信账户");
        return user;
    }
}
