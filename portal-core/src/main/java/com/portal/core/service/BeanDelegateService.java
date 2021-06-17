package com.portal.core.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * BeanDelegateService
 *
 * @author Mrhan
 * @date 2021/6/17 19:05
 */
public class BeanDelegateService extends AbstractService{

    private final Map<String, Method> serviceIdMethodMap;

    private final Class<?> delegateClass;
    public BeanDelegateService(String serviceName, Object bean, Class<?> delegateClass) {
        setServiceName(serviceName);
        setServiceObject(bean);
        serviceIdMethodMap = new HashMap<>();
        this.delegateClass = delegateClass;
    }

    public BeanDelegateService register(String methodName, Class<?> ...type) throws NoSuchMethodException {
        serviceIdMethodMap.put(methodName, delegateClass.getMethod(methodName, type));
        return this;
    }

    @Override
    public Object invoke(String id, Object... args) throws InvocationTargetException, IllegalAccessException {
        Method method = null;
        method = serviceIdMethodMap.get(id);
        return method.invoke(getServiceObject(), args);
    }

    @Override
    public Type[] getParamTypes(String id) {
        return serviceIdMethodMap.get(id).getParameterTypes();
    }
}
