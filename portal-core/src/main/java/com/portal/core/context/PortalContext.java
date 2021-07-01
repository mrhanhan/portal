package com.portal.core.context;

import com.portal.core.ExceptionHandler;
import com.portal.core.service.ServiceContainer;

/**
 * PortalContext
 * 传送门上下文
 * @author Mrhan
 * @date 2021/7/1 11:18
 */
public interface PortalContext extends PortalLifeCycle, ServiceContainer, ExceptionHandler {
    /**
     * 注册生命周期回调
     * @param cycle 注册生命周期回调
     */
    void registerLifeCycle(PortalLifeCycle cycle);

    /**
     * 添加参数序列化转换程序
     * @param paramSerialization 参数序列化转换器
     */
    void addParamSerialization(ParamSerialization<?> paramSerialization);

    /**
     * 添加对象序列化转换程序
     * @param objectSerialization 对象序列化转换
     */
    void addObjectSerialization(ObjectSerialization<?> objectSerialization);

    /**
     * 启动
     */
    void startUp();

}
