package com.portal.core.connect;

import com.portal.core.service.ServiceContainer;

/**
 * ConnectionSession
 * Session 是实现传递非序列化对象的关键，读取、操作、写入其他计算机资源的关键
 * @author Mrhan
 * @date 2021/6/15 17:31
 */
public interface ConnectionSession {

    /**
     * 获取SessionKey
     * @return  SessionKey
     */
    SessionKey getSessionKey();

    /**
     * 回去会话的ServiceContainer
     * @return  返回ServiceContainer
     */
    ServiceContainer getServiceContainer();
}
