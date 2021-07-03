package com.portal.core.context.send;

import com.portal.core.connect.Connection;
import com.portal.core.model.Data;
import com.portal.core.model.Param;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * SendInvokeData
 * 发送调用数据
 * @author Mrhan
 * @date 2021/7/4 1:44
 */
public interface SendInvokeData {
    /**
     * 发起调用
     * @param data              发起调用
     * @param connection        发起调用
     * @param resultConsumer    响应: resultConsumer
     * @throws IOException 可能调用失败
     */
    void invokeSend(Data data, Connection connection, Consumer<Param> resultConsumer) throws IOException;

}
