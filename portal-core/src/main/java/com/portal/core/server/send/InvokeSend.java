package com.portal.core.server.send;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.Data;
import com.portal.core.server.DataMonitorRegister;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * InvokeSend
 * 使用在客户端
 * @author Mrhan
 * @date 2021/6/17 16:11
 */
public interface InvokeSend extends Send, DataMonitorRegister{
    /**
     * 发起调用
     * @param data              发起调用
     * @param connection        发起调用
     * @param resultConsumer    响应: resultConsumer
     * @throws IOException 可能调用失败
     */
    void invokeSend(Data<?> data, Connection connection, Consumer<Param> resultConsumer) throws IOException;

}
