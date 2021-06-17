package com.portal.core.server;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.invoker.Invoker;
import com.portal.core.server.monitor.DataMonitor;
import com.portal.core.server.send.CallingManager;
import com.portal.core.server.send.ResultSend;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * InvokeDataHandler
 *
 * @author Mrhan
 * @date 2021/6/17 17:13
 */
@RequiredArgsConstructor
public class InvokeDataHandler implements DataHandler{

    public final ProtocolDataHandler<Data<?>> protocolDataHandler;
    @Getter
    public final Invoker invoker;
    @Getter
    public final ResultSend resultSend;

    @Override
    public void onHandler(DataMonitor monitor, byte[] data) {
        // 处理请求，或者处理响应
        Data<? extends Data<?>> convertToData = protocolDataHandler.serial(monitor, data);
        // 是否是响应数据
        if (convertToData.isResultData()) {
            // 响应调用
            replyInvoked(convertToData, monitor.getConnection());
            return;
        }
        // 根据Data进行调用
        Param invoke = invoker.invoke(convertToData);
        // 进行响应
        Data<?> result = convertToData.result(invoke);
        // 写入响应数据
        resultSend.resultSend(result, monitor.getConnection(), result.getProtocol());
    }

    /**
     * 响应调用
     * @param result        调用结果
     * @param connection    连接对象
     */
    protected void replyInvoked(Data<?> result, Connection connection) {
        CallingManager callingManager = connection.getCallingManager();
        callingManager.reply(result.getService(), result.getServiceId(), result.getId(), result.getResult());
    }
}
