package com.portal.core.server.send;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.Protocol;
import com.portal.core.server.Data;

/**
 * InvokeSend
 * 响应发送接口，用于在服务端
 * @author Mrhan
 * @date 2021/6/17 16:11
 */
public interface ResultSend extends Send{
    /**
     * 发起调用
     * @param data              包含响应信息的内容
     * @param connection        发起调用
     * @param protocol          协议
     */
    void resultSend(Data<?> data, Connection connection, Protocol<?> protocol);
}
