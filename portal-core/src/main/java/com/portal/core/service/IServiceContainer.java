package com.portal.core.service;

/**
 * IServiceRegister
 * 服务注册
 * @author Mrhan
 * @date 2021/6/10 11:28
 */
public interface IServiceContainer {
    /**
     * 注册服务
     * @param service   注册服务
     */
    void register(IService service);

    /**
     * 根据服务名称获取服务对象
     * @param serviceName   服务对象
     * @return              返回服务信息
     */
    IService getService(String serviceName);
}
