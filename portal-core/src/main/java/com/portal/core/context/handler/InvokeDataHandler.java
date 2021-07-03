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
    public void onHandler(DataMonitor monitor, Data data) {
        // 是否是响应数据
        if (data.getOperate() == Data.RETURN) {
            System.out.println("响应数据：" + data);
            // 响应调用
            replyInvoked(data, monitor.getConnection());
            return;
        }
        System.out.println("执行本地方法：" + data);
        // 根据Data进行调用
        Param[] invokeReturns = invoker.invoke(data);
        System.out.println("执行本地方法结束：" + invokeReturns);
        // 进行响应
        Data result = createReturnData(invokeReturns, data);
        System.out.println("调用结束：" + result);
        // 写入响应数据
        monitor.getSendResultData().send(result);
    }

    /**
     * 创建响应数据
     * @param invoke    回调方法
     * @param data      调用数据
     * @return          返回内容
     */
    private Data createReturnData(Param[] invoke, Data data) {
        data.setParams(invoke);
        data.setOperate(Data.RETURN);
        return data;
    }

    /**
     * 响应调用
     * @param result        调用结果
     * @param connection    连接对象
     */
    protected void replyInvoked(Data result, Connection connection) {
        CallingManager callingManager = connection.getCallingManager();
        callingManager.reply(result.getServiceName(), result.getServiceId(), result.getId(), result);
    }
}
