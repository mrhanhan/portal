package com.portal.core.context.handler;

import com.portal.core.connect.Connection;
import com.portal.core.context.DataHandler;
import com.portal.core.context.invoker.Invoker;
import com.portal.core.context.monitor.DataMonitor;
import com.portal.core.context.send.CallingManager;
import com.portal.core.model.Data;
import com.portal.core.model.Param;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * InvokeDataHandler
 *
 * @author Mrhan
 * @date 2021/6/17 17:13
 */
@RequiredArgsConstructor
public class InvokeDataHandler implements DataHandler {

    @Getter
    public final Invoker invoker;

    @Override
    public void onHandler(DataMonitor monitor, byte[] data) {
        // 处理请求，或者处理响应
        Data convertToData = null;
        if (convertToData == null) {
            return;
        }
        // 是否是响应数据
//        if (convertToData.isResultData()) {
//            // 响应调用
//            replyInvoked(convertToData, monitor.getConnection());
//            return;
//        }
        // 根据Data进行调用
        Param invoke = invoker.invoke(convertToData);
        // 进行响应
        Data result = null /*convertToData.result(invoke)*/;
        // 写入响应数据
        monitor.getSendResultData().send(result);
    }

    /**
     * 响应调用
     * @param result        调用结果
     * @param connection    连接对象
     */
    protected void replyInvoked(Data result, Connection connection) {
        CallingManager callingManager = connection.getCallingManager();
//        callingManager.reply(result.getService(), result.getServiceId(), result.getId(), result.getResult());
    }
}
