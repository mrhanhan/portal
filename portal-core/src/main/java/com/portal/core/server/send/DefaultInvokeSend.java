package com.portal.core.server.send;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.JsonProtocol;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.Data;
import com.portal.core.server.DataMonitorRegister;
import com.portal.core.server.InvokeDataHandler;
import com.portal.core.server.MultipleProtocolDataHandler;
import com.portal.core.server.SimpleDataMonitorRegister;
import com.portal.core.server.invoker.DefaultInvoker;
import com.portal.core.server.invoker.Invoker;
import com.portal.core.server.monitor.DataMonitor;
import com.portal.core.server.monitor.SimpleDataMonitor;
import com.portal.core.utils.NameThreadFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * DefaultInvokeSend
 *
 * @author Mrhan
 * @date 2021/6/17 17:49
 */
public class DefaultInvokeSend implements InvokeSend {

    private final ExecutorService executorService;

    @Setter
    @Getter
    private DataMonitorRegister dataMonitorRegister;

    @Setter
    @Getter
    private MultipleProtocolDataHandler multipleProtocolDataHandler;

    @Setter
    @Getter
    private ResultSend resultSend;

    public DefaultInvokeSend() {
        executorService = createExecutorService();
        dataMonitorRegister = new SimpleDataMonitorRegister(executorService);
        multipleProtocolDataHandler = new MultipleProtocolDataHandler();
        // 注册协议
        multipleProtocolDataHandler.registerProtocolDataHandler(new JsonProtocol().getProtocolDataHandler());
        resultSend = new DefaultResultSend(multipleProtocolDataHandler, (e) -> e.printStackTrace());
    }


    @Override
    public void invokeSend(Data<?> data, Connection connection, Consumer<Param> resultConsumer) throws IOException {
        long time = System.currentTimeMillis();

        // 序列化数据
        byte[] bytes = multipleProtocolDataHandler.deSerial(data);
        System.out.println("1 " + (System.currentTimeMillis() - time));
        Invoker invoker = new DefaultInvoker(connection.getSession().getServiceContainer());
        InvokeDataHandler invokeDataHandler = new InvokeDataHandler(multipleProtocolDataHandler, invoker, resultSend);
        System.out.println("2 " + (System.currentTimeMillis() - time));
        // 注册监听器
        registerDataMonitor(new SimpleDataMonitor(connection, invokeDataHandler, dataMonitorRegister));
        System.out.println("3 " + (System.currentTimeMillis() - time));
        // 注册等待
        connection.getCallingManager().push(data.getService(), data.getServiceId(), data.getId(), resultConsumer);
        //  发送数据
        sendData(connection, bytes);
        System.out.println(System.currentTimeMillis() - time);
    }

    /**
     * 发送数据
     *
     * @param connection 数据
     * @param bytes      发送数据
     */
    private void sendData(Connection connection, byte[] bytes) throws IOException {
        OutputStream output = connection.getOutput();
        output.write(bytes);
        output.flush();
    }


    protected ExecutorService createExecutorService() {
        return new ThreadPoolExecutor(3, 3, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(3), new NameThreadFactory("DefaultInvokeSend"));
    }


    @Override
    public void registerDataMonitor(DataMonitor dataMonitor) {
        dataMonitorRegister.registerDataMonitor(dataMonitor);
    }

    @Override
    public void removeDataMonitor(DataMonitor dataMonitor) {
        dataMonitorRegister.removeDataMonitor(dataMonitor);
    }

    @Override
    public void close() throws Exception {
        dataMonitorRegister.close();
    }
}
