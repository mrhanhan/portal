package com.portal.core;

import com.portal.core.connect.Connection;

/**
 * Service
 * 服务容器注册
 * @author Mrhan
 * @date 2021/6/15 17:22
 */
public interface Server {


    /**
     * 启动服务
     * @throws Exception 可能存在启动错误
     */
    void startUp() throws Exception;

    /**
     * 处理链接
     * @param connection    处理连接
     */
    void handlerConnection(Connection connection);

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
