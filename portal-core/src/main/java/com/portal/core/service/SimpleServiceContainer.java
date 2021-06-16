package com.portal.core.service;

import java.util.Hashtable;
import java.util.Map;

/**
 * AbstractServiceContainer
 *
 * @author Mrhan
 * @date 2021/6/10 11:33
 */
public class SimpleServiceContainer implements ServiceContainer {

    private Map<String, Service> serviceMap;

    public SimpleServiceContainer() {
        serviceMap = new Hashtable<>();
    }

    @Override
    public void register(Service service) {
        if (serviceMap.containsKey(service.getServiceName())) {
            throw new IllegalArgumentException("服务已存在:" + service.getServiceName());
        }
        serviceMap.put(service.getServiceName(), service);
    }

    @Override
    public Service getService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    @Override
    public void clear() {
        serviceMap.clear();
    }
}
