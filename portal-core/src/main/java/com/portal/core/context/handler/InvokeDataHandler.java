package com.portal.core.context.handler;

import com.portal.core.connect.Connection;
import com.portal.core.context.DataHandler;
import com.portal.core.context.invoker.Invoker;
import com.portal.core.context.monitor.DataMonitor;
import com.portal.core.context.send.CallingManager;
import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.utils.NameThreadFactory;
import lombok.Getter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * InvokeDataHandler
 *
 * @author Mrhan
 * @date 2021/6/17 17:13
 */
public class InvokeDataHandler implements DataHandler {

    @Getter
    public final Invoker invoker;

    private final ExecutorService executorService;

    public InvokeDataHandler(Invoker invoker) {
        this.invoker = invoker;
        BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();
        ThreadFactory factory = new NameThreadFactory("InvokeDataHandler");
        this.executorService = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, workQueue, factory);
    }

    @Override
    public void onHandler(DataMonitor monitor, Data data) {
        executorService.submit(() -> {
            // 是否是响应数据
            if (data.getOperate() == Data.RETURN) {
                System.out.println("响应数据：" + data);
                // 响应调用
                replyInvoked(data, monitor.getConnection());
                return;
            }
            System.out.println("执行本地方法：" + data);
            // 根据Data进行调用
            try {
                Param[] invokeReturns = invoker.invoke(data);
                System.out.println("执行本地方法结束：" + invokeReturns);
                // 进行响应
                Data result = createReturnData(invokeReturns, data);
                System.out.println("调用结束：" + result);
                // 写入响应数据
                monitor.getSendResultData().send(result);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });

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
