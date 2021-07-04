package com.example.chat.api;

import com.example.chat.model.Message;

/**
 * SendMessageService
 * 发送消息服务
 * @author Mrhan
 * @date 2021/7/4 12:38
 */
public interface SendMessageService {
    /**
     * 发送消息
     * @param message   发送消息
     */
    void send(Message message);
}
