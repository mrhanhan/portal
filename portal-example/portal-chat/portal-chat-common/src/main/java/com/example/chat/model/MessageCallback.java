package com.example.chat.model;

/**
 * MessageCallback
 *
 * @author Mrhan
 * @date 2021/7/4 12:40
 */
public interface MessageCallback {
    /**
     * 消息处理回掉
     * @param message   消息处理回调
     */
    void messageCallback(Message message);

    /**
     * 用户加入房间的回调
     * @param userInfo  UserInfo
     */
    void userCallback(UserInfo userInfo);
}
