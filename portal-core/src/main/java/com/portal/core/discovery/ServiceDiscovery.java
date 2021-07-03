package com.portal.core.discovery;

/**
 * ServiceDiscovery
 * 服务发现
 * 通过服务发现，可以对接口进行代理，然后调用远程的Service
 * @author Mrhan
 * @date 2021/6/30 16:27
 */
public interface ServiceDiscovery {

    /**
     * 获取服务名称
     * @param name 服务名称
     * @param cls  服务类型
     * @param <T>  服务类型泛型
     * @return  返回服务名称
     */
    <T> T getService(String name, Class<T> cls);

}
