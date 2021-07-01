package com.portal.core.context;

import com.portal.core.connect.Connection;

/**
 * ConnectionHandler
 * 连接处理器
 * @author Mrhan
 * @date 2021/7/1 15:40
 */
public interface ConnectionHandler {
    /**
     * 连接处理器
     * @param connection    连接处理器
     */
    void onConnection(Connection connection);
}
