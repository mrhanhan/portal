package com.example.simple.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * User
 *
 * @author Mrhan
 * @date 2021/6/17 19:01
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    private Account account;

    private String role;

    private int money;
    private int order;

    private NoSerial noSerial;
}
