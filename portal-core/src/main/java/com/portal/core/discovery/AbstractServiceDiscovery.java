package com.portal.core.discovery;

import com.portal.core.connect.Connection;
import com.portal.core.context.DefaultMonitorManager;
import com.portal.core.context.MonitorManager;
import com.portal.core.context.handler.InvokeDataHandler;
import com.portal.core.context.invoker.DefaultInvoker;
import com.portal.core.context.invoker.Invoker;
import com.portal.core.context.monitor.DataMonitor;
import com.portal.core.context.monitor.DefaultDataMonitor;
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
import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.utils.UniqueCodeGen;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AbstractServiceDiscovery
 *
 * @author Mrhan
 * @date 2021/6/30 16:30
 */
public abstract class AbstractServiceDiscovery implements ServiceDiscovery{

    /**
     * 检查项管理器
     */
    private MonitorManager monitorManager;
    /**
     * 数据序列化和反序列化
     */
    private MultipleParamSerialization multipleParamSerialization;
    private MultipleObjectSerialization multipleObjectSerialization;

    public AbstractServiceDiscovery() {
        monitorManager = new DefaultMonitorManager();
        onInstance();
    }

    /**
     * 实例化
     */
    protected void onInstance() {
        initializeSerialization();
    }

    @Override
    public <T> T getService(String name, Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        if (cls.isInterface()) {
            enhancer.setInterfaces(new Class[]{cls});
        } else {
            enhancer.setSuperclass(cls);
        }
        Connection connection = getConnection();
        // 注册检测项
        DataMonitor dataMonitor = registerDataMonitor(connection);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                Data data = new Data();
                data.setOperate(Data.CALL);
                data.setServiceName(name);
                data.setServiceId(method.getName());
                data.setId(UniqueCodeGen.genNumber());
                if (objects != null) {
                    Param[] params = new Param[objects.length];
                    for (int i = 0; i < objects.length; i++) {
                        params[i] = multipleParamSerialization.serial(objects[i]);
                    }
                    data.setParams(params);
                } else {
                    data.setParams(new Param[0]);
                }
                AtomicBoolean status = new AtomicBoolean(false);
                Object[] result = new Object[1];
                Object lock = new Object();
                System.out.println("发起调用:" + data);
                dataMonitor.getSendResultData().send(data, (resultData) -> {
                    Param[] params = resultData.getParams();
                    if (params.length > 0) {
                        result[0] = multipleObjectSerialization.serial(params[0], method.getReturnType());
                    }
                    status.set(true);
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                });
                if (!status.get()) {
                    synchronized (lock) {
                        lock.wait();
                    }
                }
                return result[0];
            }
        });
        return (T) enhancer.create();
    }

    /**
     * 注册检查项
     * @param connection    注册检查项
     */
    protected DataMonitor registerDataMonitor(Connection connection) {
        Invoker invoker = new DefaultInvoker(connection.getSession().getServiceContainer(), multipleObjectSerialization, multipleParamSerialization);
        InvokeDataHandler invokeDataHandler = new InvokeDataHandler(invoker);
        DefaultDataMonitor dataMonitor = new DefaultDataMonitor(connection, invokeDataHandler);
        monitorManager.addMonitor(dataMonitor, true);
        return dataMonitor;
    }
    /**
     * 初始化序列化
     */
    protected  void initializeSerialization() {
        multipleParamSerialization = new MultipleParamSerialization();
        multipleParamSerialization.add(new NullParamSerialization());
        multipleParamSerialization.add(new NumberParamSerialization());
        multipleParamSerialization.add(new StringParamSerialization());
        multipleParamSerialization.add(new ArrayParamSerialization(multipleParamSerialization));
        multipleParamSerialization.add(new CollectionParamSerialization(multipleParamSerialization));
        multipleParamSerialization.add(new ObjectParamSerialization(multipleParamSerialization));

        multipleObjectSerialization = new MultipleObjectSerialization();
        multipleObjectSerialization.add(new NullObjectSerialization());
        multipleObjectSerialization.add(new NumberObjectSerialization());
        multipleObjectSerialization.add(new StringObjectSerialization());
        multipleObjectSerialization.add(new ArrayObjectSerialization(multipleObjectSerialization));
        multipleObjectSerialization.add(new CollectionObjectSerialization(multipleObjectSerialization));
        multipleObjectSerialization.add(new ObjectObjectSerialization(multipleObjectSerialization));

    }
    /**
     * 获取发现的目标服务器连接
     * @return  返回连接对象
     */
    protected abstract Connection getConnection();


    public void close() {
        monitorManager.onShutDown(null);
    }
}
