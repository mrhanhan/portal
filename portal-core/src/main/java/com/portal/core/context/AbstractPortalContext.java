package com.portal.core.context;

import com.portal.core.connect.Connection;
import com.portal.core.context.lifecycle.PortalLifeCycleManager;
import com.portal.core.service.ServiceContainer;
import com.portal.core.service.SimpleServiceContainer;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

import java.util.HashSet;
import java.util.Set;

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
     * 数据序列化集合反序列化集合
     */
    private Set<ParamSerialization<?>> paramSerializationSet;
    private Set<ObjectSerialization<?>> objectSerializationSet;

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
        paramSerializationSet = new HashSet<>();
        objectSerializationSet = new HashSet<>();
    }

    @Override
    public void onInitialize(PortalContext context) {
        // 初始化连接处理器
        connectionHandler = new ConnectionHandler() {
            @Override
            public void onConnection(Connection connection) {

            }
        };
        // 数据处理器
        portalLifeCycleManager.onInitialize(context);
    }

    @Override
    public void onShutDown(PortalContext context) {
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

    /**
     * 获取连接检测项
     * @param connectionHandler 连接检测项
     * @return  返回连接检测项
     */
    protected abstract ConnectionMonitor getConnectionMonitor(ConnectionHandler connectionHandler);


    @Override
    public void addObjectSerialization(ObjectSerialization<?> objectSerialization) {
        this.objectSerializationSet.add(objectSerialization);
    }

    @Override
    public void addParamSerialization(ParamSerialization<?> paramSerialization) {
        this.paramSerializationSet.add(paramSerialization);
    }

    @Override
    public void registerLifeCycle(PortalLifeCycle cycle) {
        portalLifeCycleManager.registerLifeCycle(cycle);
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }
}