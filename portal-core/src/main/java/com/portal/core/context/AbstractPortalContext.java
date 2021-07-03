package com.portal.core.context;

import com.portal.core.context.handler.InvokeDataHandler;
import com.portal.core.context.invoker.DefaultInvoker;
import com.portal.core.context.invoker.Invoker;
import com.portal.core.context.lifecycle.PortalLifeCycleManager;
import com.portal.core.context.monitor.DefaultDataMonitor;
import com.portal.core.context.serial.AbstractObjectSerialization;
import com.portal.core.context.serial.AbstractParamSerialization;
import com.portal.core.context.serial.ArrayObjectSerialization;
import com.portal.core.context.serial.ArrayParamSerialization;
import com.portal.core.context.serial.CollectionObjectSerialization;
import com.portal.core.context.serial.CollectionParamSerialization;
import com.portal.core.context.serial.MultipleObjectSerialization;
import com.portal.core.context.serial.MultipleParamSerialization;
import com.portal.core.context.serial.NullObjectSerialization;
import com.portal.core.context.serial.NullParamSerialization;
import com.portal.core.context.serial.NumberObjectSerialization;
import com.portal.core.context.serial.NumberParamSerialization;
import com.portal.core.context.serial.ObjectObjectSerialization;
import com.portal.core.context.serial.ObjectParamSerialization;
import com.portal.core.context.serial.StringObjectSerialization;
import com.portal.core.context.serial.StringParamSerialization;
import com.portal.core.service.ServiceContainer;
import com.portal.core.service.SimpleServiceContainer;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

/**
 * AbstractPortalContext
 *
 * @author Mrhan
 * @date 2021/7/1 15:23
 */
public abstract class AbstractPortalContext implements PortalContext{

    @Delegate(types = {ServiceContainer.class})
    private SimpleServiceContainer simpleServiceContainer;
    /**
     * 传送门生命周期管理器
     */
    private PortalLifeCycleManager portalLifeCycleManager;
    /**
     * 检测项管理器
     */
    private MonitorManager monitorManager;
    /**
     * 连接处理器程序
     */
    private ConnectionHandler connectionHandler;
    /**
     * 初始化连接管理器
     */
    private ConnectionMonitor connectionMonitor;
    /**
     * 数据处理器
     */
    private DataHandler dataHandler;
    /**
     * 数据序列化和反序列化
     */
    private MultipleParamSerialization multipleParamSerialization;
    private MultipleObjectSerialization multipleObjectSerialization;


    private final Object statusLock = new Object();

    public AbstractPortalContext() {
        this.onInstance();
    }

    /**
     * 实例化，初始化各种管理器容器
     */
    protected void onInstance() {
        // 创建LifeCycleManager
        portalLifeCycleManager = new PortalLifeCycleManager();
        // 监听器管理器
        monitorManager = new DefaultMonitorManager();
        registerLifeCycle(monitorManager);
        // 初始化服务容器
        simpleServiceContainer = new SimpleServiceContainer();
        // 序列化和反序列化集合
        multipleParamSerialization = new MultipleParamSerialization();
        multipleObjectSerialization = new MultipleObjectSerialization();
        // 初始化序列化信息
        initializeSerialization();
        // 初始化数据处理器
        dataHandler = new InvokeDataHandler(createDefaultInvoker());
    }

    @Override
    public void onInitialize(PortalContext context) {
        // 初始化连接处理器
        connectionHandler = (connection) -> {
            System.out.println("监听到连接：" + connection);
            monitorManager.addMonitor(new DefaultDataMonitor(connection, dataHandler), true);
        };
        // 数据处理器
        portalLifeCycleManager.onInitialize(context);
    }

    @Override
    public void onShutDown(PortalContext context) {
        // 关闭连接检测器
        try {
            connectionMonitor.close();
        } catch (Exception e) {
            onException(e);
        }
        portalLifeCycleManager.onShutDown(context);
    }

    @Override
    public void onStartup(PortalContext context) {
        // 初始化连接监听器
        connectionMonitor = getConnectionMonitor(connectionHandler);
        // 注册检测项
        monitorManager.addMonitor(connectionMonitor, true);
        // 调用管理器的启动
        portalLifeCycleManager.onStartup(context);
    }

    @SneakyThrows
    @Override
    public void startUp() {
        this.onInitialize(this);
        this.onStartup(this);
        // 等待
        try  {
            synchronized (statusLock) {
                statusLock.wait();
            }
        } finally {
            this.onShutDown(this);
        }
    }

    @Override
    public void shutDown() {
        synchronized (statusLock) {
            statusLock.notify();
        }
    }

    /**
     * 获取连接检测项
     * @param connectionHandler 连接检测项
     * @return  返回连接检测项
     */
    protected abstract ConnectionMonitor getConnectionMonitor(ConnectionHandler connectionHandler);


    @Override
    public void addObjectSerialization(AbstractObjectSerialization<?> objectSerialization) {
        this.multipleObjectSerialization.add(objectSerialization);
    }

    @Override
    public void addParamSerialization(AbstractParamSerialization<?> paramSerialization) {
        this.multipleParamSerialization.add(paramSerialization);
    }

    @Override
    public void registerLifeCycle(PortalLifeCycle cycle) {
        portalLifeCycleManager.registerLifeCycle(cycle);
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }

    /**
     * 初始化序列化
     */
    protected  void initializeSerialization() {
        addParamSerialization(new NullParamSerialization());
        addParamSerialization(new NumberParamSerialization());
        addParamSerialization(new StringParamSerialization());
        addParamSerialization(new ArrayParamSerialization(this.multipleParamSerialization));
        addParamSerialization(new CollectionParamSerialization(this.multipleParamSerialization));
        addParamSerialization(new ObjectParamSerialization(this.multipleParamSerialization));

        addObjectSerialization(new NullObjectSerialization());
        addObjectSerialization(new NumberObjectSerialization());
        addObjectSerialization(new StringObjectSerialization());
        addObjectSerialization(new ArrayObjectSerialization(this.multipleObjectSerialization));
        addObjectSerialization(new CollectionObjectSerialization(this.multipleObjectSerialization));
        addObjectSerialization(new ObjectObjectSerialization(this.multipleObjectSerialization));

    }

    /**
     * 创建默认得Invoker
     * @return  Invoker
     */
    protected Invoker createDefaultInvoker() {
        return new DefaultInvoker(this, multipleObjectSerialization, multipleParamSerialization);
    }
}
