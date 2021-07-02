package com.portal.core.context.serial;

import lombok.Data;

import java.io.Serializable;

/**
 * User
 *
 * @author Mrhan
 * @date 2021/7/2 17:38
 */
@Data
public class User implements Serializable {
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * Account
     */
    private Account account;
}
