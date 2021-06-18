package com.portal.core.server;

import com.portal.core.Portal;
import com.portal.core.connect.Connection;
import com.portal.core.protocol.JsonProtocol;
import com.portal.core.protocol.Protocol;
import com.portal.core.protocol.SimpleTextProtocol;
import com.portal.core.server.invoker.DefaultInvoker;
import com.portal.core.server.invoker.Invoker;
import com.portal.core.server.monitor.SimpleDataMonitor;
import com.portal.core.server.send.DefaultResultSend;
import com.portal.core.server.send.ResultSend;
import com.portal.core.service.ServiceContainer;
import com.portal.core.service.SimpleServiceContainer;
import com.portal.core.utils.NameThreadFactory;
import lombok.experimental.Delegate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AbstractServer
 *
 * @author Mrhan
 * @date 2021/6/15 17:47
 */
public abstract class AbstractPortal implements Portal {

    @Delegate
    private ServiceContainer simpleServiceContainer;
    /**
     * 调用者
     */
    private Invoker invoker;
    /**
     * 执行服务
     */
    private ExecutorService executorService;
    /**
     * 协议数据处理器
     */
    private MultipleProtocolDataHandler multipleProtocolDataHandler;
    /**
     * 连接检查项
     */
    private ConnectionMonitor connectionMonitor;
    /**
     * ResultSend
     */
    private ResultSend resultSend;
    /**
     * 调用数据处理器
     */
    @Delegate(types = { DataHandler.class })
    private InvokeDataHandler invokeDataHandler;
    @Delegate
    private DataMonitorRegister dataMonitorRegister;
    /**
     * 状态锁
     */
    private final Object statusLock = new Object();

    public AbstractPortal() {
        try {
            bootstrap();
        } catch (Exception e) {
            onException(e);
        }
    }


    /**
     * 引导启动，初始化各种程序
     */
    protected void bootstrap() {
        // 初始化异步服务
        executorService = createExecutorService();
        // 初始化服务注册容器
        simpleServiceContainer = createServiceContainer();
        multipleProtocolDataHandler = createMultipleProtocolDataHandler();
        // 初始化连接检查项
        connectionMonitor = createConnectionMonitor();
        // 检测器
        dataMonitorRegister = new SimpleDataMonitorRegister(executorService);
        // 初始化调用者
        invoker = createInvoker();
        // resultSend
        resultSend = createResultSend();
        // 初始化调用数据处理器
        invokeDataHandler = createInvokerDataHandler();
    }

    @Override
    public final void startUp() throws Exception {
        // 启动事件
        onStart();
        // 启动连接检查项
        executorService.submit(connectionMonitor);
        // 让线程等待
        synchronized (statusLock) {
            statusLock.wait();
        }
        try {
            onClose();
        } catch (Exception e) {
            onException(e);
        } finally {
            // 关闭所有检查项
            dataMonitorRegister.close();
            // 关闭连接检查项
            try {
                connectionMonitor.close();
            } catch (Exception e) {
                onException(e);
            } finally {
                try {
                    // 关闭服务
                    executorService.shutdownNow();
                } catch (Exception e) {
                    onException(e);
                }
            }
        }
    }

    @Override
    public final void shutDown() {
        synchronized (statusLock) {
            statusLock.notify();
        }
    }

    /**
     * 启动
     *
     * @throws Exception 可能启动会出现移除
     */
    protected  void onStart() throws Exception {
        // 注册协议
        registerProtocol();
    }

    /**
     * 关闭
     *
     * @throws Exception 可能启动会出现移除
     */
    protected  void onClose() throws Exception {
    }

    @Override
    public void addProtocol(Protocol<?> protocol) {
        registerProtocolDataHandler(protocol.getProtocolDataHandler());
    }

    /**
     * 注册默认协议
     */
    protected  void registerProtocol() {
        // simple 协议
        addProtocol(new SimpleTextProtocol());
        // json 协议
        addProtocol(new JsonProtocol());
    }

    /**
     * 初始化连接检查项，检测各种连接信息
     *
     * @return 连接检查项
     */
    protected abstract ConnectionMonitor createConnectionMonitor();

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onHandler(Connection connection) {
        registerDataMonitor(new SimpleDataMonitor(connection, this, this, this));
    }

    /**
     * 创建异步执行服务
     *
     * @return ExecutorService
     */
    protected ExecutorService createExecutorService() {
        return new ThreadPoolExecutor(100, 10000, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000), new NameThreadFactory(getClass().getSimpleName()));
    }
    /**
     * 创建默认的服务容器
     * @return  服务容器
     */
    protected  ServiceContainer createServiceContainer() {
        return new SimpleServiceContainer();
    }

    /**
     * 创建调用数据处理程序
     * @return  调用数据处理程序
     */
    protected InvokeDataHandler createInvokerDataHandler() {
        return new InvokeDataHandler(multipleProtocolDataHandler, invoker, resultSend);
    }

    /**
     * 创建ResultSend
     * @return  ResultSend
     */
    protected ResultSend createResultSend() {
        return new DefaultResultSend(multipleProtocolDataHandler, this);
    }
    /**
     * 创建默认的Invoker
     * @return  Invoker
     */
    protected  Invoker createInvoker() {
        return new DefaultInvoker(this);
    }

    protected MultipleProtocolDataHandler createMultipleProtocolDataHandler() {
        return new MultipleProtocolDataHandler();
    }

    @Override
    public void registerProtocolDataHandler(ProtocolDataHandler<?> protocolDataHandler) {
        multipleProtocolDataHandler.registerProtocolDataHandler(protocolDataHandler);
    }

    @Override
    public void removeProtocolDataHandler(ProtocolDataHandler<?> protocolDataHandler) {
        multipleProtocolDataHandler.registerProtocolDataHandler(protocolDataHandler);
    }
}
