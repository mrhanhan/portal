package com.portal.core.service;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * MethodDelegateService
 * 方法代理
 * @author Mrhan
 * @date 2021/6/10 11:42
 */
public class MethodDelegateService extends AbstractService {

    private final Method method;

    public MethodDelegateService(Method method, String serviceName, Object serviceObject) {
        this.method = method;
        setServiceName(serviceName);
        setServiceObject(serviceObject);
    }

    @SneakyThrows
    public MethodDelegateService(String serviceName, Object serviceObject, String methodName, Class<?> ...argTypes) {
        this.method = serviceObject.getClass().getMethod(methodName, argTypes);
        setServiceName(serviceName);
        setServiceObject(serviceObject);
    }



    @Override
    public Object invoke(String id, Object... arg) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(getServiceObject(), arg);
    }
}
