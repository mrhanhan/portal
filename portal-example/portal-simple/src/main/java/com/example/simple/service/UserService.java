package com.example.simple.service;

import com.example.simple.model.NoSerial;
import com.example.simple.model.User;

import java.io.File;
import java.io.OutputStream;

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

    /**
     * 获取用户信息
     * @param id    获取用户信息
     * @return  User
     */
    User getUserInfo(Long id);


    NoSerial test();

    File file();

    OutputStream output();

    String printOutput();
}
