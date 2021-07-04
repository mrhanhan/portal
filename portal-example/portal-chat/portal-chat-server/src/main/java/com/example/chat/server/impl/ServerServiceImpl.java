package com.example.chat.server.impl;

import com.example.chat.api.RegisterClientService;
import com.example.chat.api.SendMessageService;
import com.example.chat.model.Message;
import com.example.chat.model.MessageCallback;
import com.example.chat.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * RegisterClientServiceImpl
 *
 * @author Mrhan
 * @date 2021/7/4 12:47
 */
public class ServerServiceImpl implements RegisterClientService, SendMessageService {

    private List<MessageCallback> callbackList;

    public ServerServiceImpl() {
        callbackList = new ArrayList<>();
    }

    @Override
    public void register(UserInfo userInfo, MessageCallback callback) {
        for (MessageCallback messageCallback : callbackList) {
            messageCallback.userCallback(userInfo);
        }
        callbackList.add(callback);
        Message message = new Message();
        message.setContent("欢迎加入");
        message.setUserInfo(new UserInfo("系统"));
        callback.messageCallback(message);
    }

    @Override
    public void send(Message message) {
        for (MessageCallback messageCallback : callbackList) {
            try {
                messageCallback.messageCallback(message);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
