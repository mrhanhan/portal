package com.portal.core.service;

import java.lang.reflect.InvocationTargetException;

/**
 * IService
 * 服务
 * @author Mrhan
 * @date 2021/6/10 11:26
 */
public interface IService {
    /**
     * 获取服务名称
     * @return  服务名称
     */
    String getServiceName();

    /**
     * 获取具体服务的对象
     * @return  具体服务的对象
     */
    Object getServiceObject();
    /**
     * 调用服务
     * @param id    调用ID
     * @param arg   参数
     * @return      返回调用的参数
     */
    Object invoke(String id, Object ...arg) throws InvocationTargetException, IllegalAccessException;
}
