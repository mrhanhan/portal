package com.portal.core;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.Protocol;
import com.portal.core.server.ConnectionHandler;
import com.portal.core.server.DataHandler;
import com.portal.core.server.DataMonitorRegister;
import com.portal.core.server.ProtocolDataHandlerRegister;
import com.portal.core.service.ServiceContainer;

/**
 * Service
 * 服务容器注册
 * @author Mrhan
 * @date 2021/6/15 17:22
 */
public interface Portal extends ConnectionHandler<Connection>, ProtocolDataHandlerRegister, DataHandler, DataMonitorRegister, ServiceContainer {

    /**
     * 添加一些
     * @param protocol  Protocol
     */
    void addProtocol(Protocol<?> protocol);

    /**
     * 启动服务
     * @throws Exception 可能存在启动错误
     */
    void startUp() throws Exception;

    /**
     * 关闭
     */
    void shutDown();

    /**
     * 统一异常处理
     * @param e 异常处理
     */
    void handleException(Exception e);

}
