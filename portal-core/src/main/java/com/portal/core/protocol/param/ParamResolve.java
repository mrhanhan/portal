package com.portal.core.protocol.param;

import com.portal.core.server.Data;
import com.portal.core.service.ServiceContainer;

import java.lang.reflect.Type;

/**
 * ParamResolve
 *
 * @author Mrhan
 * @date 2021/6/17 8:57
 */
public interface ParamResolve {
    /**
     * 解析Param 为Java类型的参数
     * @param data  数据
     * @param param Param
     * @param cls   参数类型
     * @param <T>   泛型T
     * @return  返回解析的T类型对象
     */
    <T> T resolve(Data<?> data, Param param, Type cls);

    /**
     * 对象解析为 Param
     * @param obj              Object对象
     * @param serviceContainer 代理不可序列化的对象
     * @return                 返回Param
     */
    Param resolve(Object obj, ServiceContainer serviceContainer);
}
