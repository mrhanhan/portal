package com.portal.core.connect;

import com.portal.core.ISupport;

import java.io.Closeable;

/**
 * ConnectionManager
 *
 * @author Mrhan
 * @date 2021/6/15 16:37
 */
public interface ConnectionManager extends Closeable, ISupport<ConnectMetadata> {
    /**
     * 获取连接对象
     * @param metadata  获取连接对象
     * @return          返回连接对象
     */
    IConnection getConnection(ConnectMetadata metadata) throws Exception;

}
