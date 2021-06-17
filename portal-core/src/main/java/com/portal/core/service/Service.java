package com.portal.core.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/**
 * IService
 * 服务
 * @author Mrhan
 * @date 2021/6/10 11:26
 */
public interface Service {
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
     * @param args   参数
     * @return      返回调用的参数
     * @throws InvocationTargetException  1
     * @throws IllegalAccessException 2
     */
    Object invoke(String id, Object ...args) throws InvocationTargetException, IllegalAccessException;

    /**
     * 返回指定服务ID的方法参数
     * @param id    服务 ID
     * @return      返回参数信息
     */
    Type[] getParamTypes(String id);
}
