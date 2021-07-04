package com.example.chat.api;

import com.example.chat.model.MessageCallback;
import com.example.chat.model.UserInfo;

/**
 * RegisterClientService
 * 注册客户端服务
 * @author Mrhan
 * @date 2021/7/4 12:39
 */
public interface RegisterClientService {
    /**
     * 消息处理回调
     * @param userInfo  用户信息
     * @param callback  消息回调方法
     */
    void register(UserInfo userInfo, MessageCallback callback);
}
