package com.example.simple.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Account
 *
 * @author Mrhan
 * @date 2021/6/30 17:31
 */
@Data
@Accessors(chain = true)
public class Account {

    private String type;
    private String number;
    private int balance;
    private String username;
}
