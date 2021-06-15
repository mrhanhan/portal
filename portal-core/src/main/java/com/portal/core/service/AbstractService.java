package com.portal.core.service;

/**
 * AbstractService
 *
 * @author Mrhan
 * @date 2021/6/10 11:31
 */
public abstract class AbstractService implements IService{
    /**
     * 服务名称
     */
    private String serviceName;

    private Object serviceObject;

    @Override
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(Object serviceObject) {
        this.serviceObject = serviceObject;
    }
}
