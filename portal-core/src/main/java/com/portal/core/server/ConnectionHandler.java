package com.portal.core.server;

import com.portal.core.connect.Connection;

/**
 * ConnectionHandler
 * 处理连接
 * @author Mrhan
 * @date 2021/6/15 20:26
 */
public interface ConnectionHandler<T extends Connection> {
    /**
     * 处理连接程序
     * @param connection    处理连接程序
     */
    void onHandler(Connection connection);
}
