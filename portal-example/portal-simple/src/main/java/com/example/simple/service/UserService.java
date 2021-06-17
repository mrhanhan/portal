package com.example.simple.service;

import com.example.simple.model.User;

/**
 * UserService
 *
 * @author Mrhan
 * @date 2021/6/17 19:01
 */
public interface UserService {
    /**
     * 用户登录
     * @param user  用户信息
     * @return  返回登录后的用户信息
     */
    User login(User user);
}
