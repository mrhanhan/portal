package com.portal.core.context.send;


import com.portal.core.model.Data;

/**
 * SendResultData
 * 发送响应数据
 * @author Mrhan
 * @date 2021/7/1 16:16
 */
public interface SendResultData {
    /**
     * 发送数据
     * @param data  发送数据
     */
    void send(Data data);
}
