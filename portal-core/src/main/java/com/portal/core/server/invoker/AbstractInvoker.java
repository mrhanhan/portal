package com.portal.core.server.invoker;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.param.Param;
import com.portal.core.protocol.param.ParamResolve;
import com.portal.core.server.Data;
import com.portal.core.service.Service;
import com.portal.core.service.ServiceContainer;

import java.lang.reflect.Type;

/**
 * AbstractInvoker
 *
 * @author Mrhan
 * @date 2021/6/17 8:59
 */
public abstract class AbstractInvoker implements Invoker {

    private final ServiceContainer serviceContainer;
    private final ParamResolve resolve;

    public AbstractInvoker(ServiceContainer serviceContainer) {
        this.serviceContainer = serviceContainer;
        resolve = getParamResolve();
    }

    /**
     * 获取参数解析器
     *
     * @return 参数解析器
     */
    protected abstract ParamResolve getParamResolve();

    @Override
    public Param invoke(Data<?> data) {
        // 获取服务
        Service service = getService(data);
        if (service == null) {
            // 抛出移除
            return resolve.resolve(new NullPointerException("Service Not Found"), getServiceContainer(data));
        }
        // 解析参数
        Object[] args = resolveArguments(data.getParamArray(), service.getParamTypes(data.getServiceId()));
        // 执行调用
        try {
            Object invoke = service.invoke(data.getServiceId(), args);
            // 返回调用的结果
            return resolve.resolve(invoke, getServiceContainer(data));
        } catch (Exception e) {
            e.printStackTrace();
            return resolve.resolve(e, getServiceContainer(data));
        }
    }

    /**
     * 解析参数
     *
     * @param paramArray Param[]
     * @param paramTypes Type[]
     * @return Object[]
     */
    protected Object[] resolveArguments(Param[] paramArray, Type[] paramTypes) {
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = resolve.resolve(paramArray[i], paramTypes[i]);
        }
        return args;
    }

    /**
     * 获取服务容器
     *
     * @param data Data
     * @return ServiceContainer
     */
    protected ServiceContainer getServiceContainer(Data<?> data) {
        if (data.getConnection().getSession() != null) {
            return data.getConnection().getSession().getServiceContainer();
        } else {
            return serviceContainer;
        }
    }

    /**
     * 获取需要执行的服务
     *
     * @param data data
     * @return 返回执行的服务
     */
    protected Service getService(Data<?> data) {
        Connection connection = data.getConnection();
        if (connection.getSession() != null) {
            Service service = connection.getSession().getServiceContainer().getService(data.getService());
            if (service != null) {
                return service;
            }
        }
        return serviceContainer.getService(data.getService());
    }
}
