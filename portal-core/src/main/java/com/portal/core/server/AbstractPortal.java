package com.portal.core.server;

import com.portal.core.Portal;
import com.portal.core.connect.Connection;
import com.portal.core.protocol.JsonProtocol;
import com.portal.core.protocol.Protocol;
import com.portal.core.protocol.SimpleTextProtocol;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.invoker.DefaultInvoker;
import com.portal.core.server.invoker.Invoker;
import com.portal.core.server.monitor.DataMonitor;
import com.portal.core.server.monitor.SimpleDataMonitor;
import com.portal.core.service.ServiceContainer;
import com.portal.core.service.SimpleServiceContainer;
import lombok.experimental.Delegate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * AbstractServer
 *
 * @author Mrhan
 * @date 2021/6/15 17:47
 */
public abstract class AbstractPortal implements Portal, ProtocolDataHandler<Data> {

    @Delegate
    private DefaultProtocolDataHandlerRegister protocolDataHandlerRegister;
    @Delegate
    private ServiceContainer simpleServiceContainer;

    private Invoker invoker;
    /**
     * 执行服务
     */
    private ExecutorService executorService;
    /**
     * 数据检测项
     */
    private final Set<DataMonitor> dataMonitorSet;
    /**
     * 连接检查项
     */
    private ConnectionMonitor connectionMonitor;
    /**
     * 状态锁
     */
    private final Object statusLock = new Object();

    public AbstractPortal() {
        // 初始化数据检测箱
        dataMonitorSet = new HashSet<>();
        try {
            bootstrap();
        } catch (Exception e) {
            handleException(e);
        }
    }


    /**
     * 引导启动，初始化各种程序
     */
    protected void bootstrap() {
        // 初始化异步服务
        executorService = createExecutorService();
        // 初始化，协议注册程序
        protocolDataHandlerRegister = createProtocolDataHandlerRegister();
        // 初始化服务注册容器
        simpleServiceContainer = createServiceContainer();
        // 初始化连接检查项
        connectionMonitor = createConnectionMonitor();
        // 初始化调用者
        invoker = createInvoker();
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
            handleException(e);
        } finally {
            // 关闭所有检查项
            dataMonitorSet.forEach((m) -> {
                try {
                    m.close();
                } catch (Exception e) {
                    handleException(e);
                }
            });
            // 关闭连接检查项
            try {
                connectionMonitor.close();
            } catch (Exception e) {
                handleException(e);
            } finally {
                try {
                    // 关闭服务
                    executorService.shutdownNow();
                } catch (Exception e) {
                    handleException(e);
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
    public void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
     * 移除数据检查项
     *
     * @param monitor 数据检查项
     */
    @Override
    public void removeDataMonitor(DataMonitor monitor) {
        dataMonitorSet.remove(monitor);
    }

    /**
     * 添加数据检查项
     *
     * @param monitor 数据检查项
     */
    @Override
    public void registerDataMonitor(DataMonitor monitor) {
        dataMonitorSet.add(monitor);
        // 检测检查项，进行检测
        executorService.submit(monitor);
    }


    @Override
    public void onHandler(Connection connection) {
        registerDataMonitor(new SimpleDataMonitor(connection, this));
    }

    /**
     * 调用请求
     *
     * @param dataMonitor 数据检测项
     * @param data        调用的数据
     * @return 返回调用数据
     */
    @Override
    public void onHandler(DataMonitor dataMonitor, byte[] data) {
        Data convertToData = serial(dataMonitor, data);
        System.out.println("DATA: " + convertToData);
        // 根据Data进行调用
        Param invoke = invoker.invoke(convertToData);
        System.out.println("PARAM: " + invoke);
        // TODO 写入调用完的数据
        try {
            dataMonitor.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 创建异步执行服务
     *
     * @return ExecutorService
     */
    protected ExecutorService createExecutorService() {
        ThreadFactory factory = (run) -> {
            Thread thread = new Thread(run);
            thread.setName("Server ExecutorService");
            return thread;
        };
        return new ThreadPoolExecutor(10, 10, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10), factory);
    }

    /**
     * 创建默认协议数据处理注册程序
     * @return  DefaultProtocolDataHandlerRegister
     */
    protected DefaultProtocolDataHandlerRegister createProtocolDataHandlerRegister() {
        return new DefaultProtocolDataHandlerRegister();
    }

    /**
     * 创建默认的服务容器
     * @return  服务容器
     */
    protected  ServiceContainer createServiceContainer() {
        return new SimpleServiceContainer();
    }

    /**
     * 执行协议数据转换器，转换byte => data
     *
     * @param dataMonitor 数据检查项
     * @param supportList 可以使用的数据转换器
     * @param data        bytes
     * @return 返回转换后的Data
     */
    protected  Data executeProtocolDataHandlerToData(DataMonitor dataMonitor, List<ProtocolDataHandler<? extends Data>> supportList, byte[] data) {
        if (supportList.isEmpty()) {
            return null;
        }
        ProtocolDataHandler dataHandler = supportList.get(0);
        return dataHandler.serial(dataMonitor, data);
    }

    /**
     * 执行协议数据转换器，转换byte => data
     *
     * @param supportList 可以使用的数据转换器
     * @param data        bytes
     * @return 返回转换后的Data
     */
    protected byte[] executeProtocolDataHandlerToByte(List<ProtocolDataHandler<? extends Data>> supportList, Data data) {
        if (supportList.isEmpty()) {
            return null;
        }
        ProtocolDataHandler dataHandler = supportList.get(0);
        return dataHandler.deSerial(data);
    }

    /* ========================================================== Invoker ====================================================================== */

    /**
     * 创建默认的Invoker
     * @return  Invoker
     */
    protected  Invoker createInvoker() {
        return new DefaultInvoker(this);
    }

    /* ========================================================== ProtocolDataHandler ========================================================== */

    @Override
    public boolean isSupport(Data data) {
        return !protocolDataHandlerRegister.getSupportList(data).isEmpty();
    }

    @Override
    public boolean isSupport(byte[] data) {
        return !protocolDataHandlerRegister.getSupportList(data).isEmpty();
    }

    @Override
    public Data serial(DataMonitor dataMonitor, byte[] data) {
        return executeProtocolDataHandlerToData(dataMonitor, protocolDataHandlerRegister.getSupportList(data), data);
    }

    @Override
    public byte[] deSerial(Data data) {
        return executeProtocolDataHandlerToByte(protocolDataHandlerRegister.getSupportList(data), data);
    }

    @Override
    public Protocol<Data> getProtocol() {
        return null;
    }
}
