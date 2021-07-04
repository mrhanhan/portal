package com.example.chat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Message
 *
 * @author Mrhan
 * @date 2021/7/4 12:40
 */
@Data
public class Message implements Serializable {
    /**
     * 发送的消息
     */
    private String content;
    /**
     * 用户信息
     */
    private UserInfo userInfo;
}
