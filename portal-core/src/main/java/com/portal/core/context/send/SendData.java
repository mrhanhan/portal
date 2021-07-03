package com.portal.core.context.send;


import com.portal.core.model.Data;

import java.util.function.Consumer;

/**
 * SendData
 * 发送响应数据
 * @author Mrhan
 * @date 2021/7/1 16:16
 */
public interface SendData {
    /**
     * 发送数据
     * @param data  发送数据
     */
    default void send(Data data) {
        send(data, null);
    }

    /**
     * 回调
     * @param data  返回数据
     * @param callback  callback
     */
    void send(Data data, Consumer<Data> callback);
}
