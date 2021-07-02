package com.portal.core.context.serial;

import lombok.Data;

import java.io.Serializable;

/**
 * Account
 *
 * @author Mrhan
 * @date 2021/7/2 17:38
 */
@Data
public class Account implements Serializable {

    private Integer balance;
}
