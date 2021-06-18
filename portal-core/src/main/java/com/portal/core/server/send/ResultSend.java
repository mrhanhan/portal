package com.portal.core.server.send;

import com.portal.core.connect.Connection;
import com.portal.core.server.Data;
import com.portal.core.server.monitor.DataMonitor;

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
     * @param monitor           数据监听器用于打包数据
     */
    void resultSend(Data<?> data, Connection connection, DataMonitor monitor);
}
