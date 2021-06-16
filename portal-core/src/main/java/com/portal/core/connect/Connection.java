package com.portal.core.connect;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IConnect
 *
 * @author Mrhan
 * @date 2021/6/9 16:27
 */
public interface Connection extends AutoCloseable {
    /**
     * 获取输入
     * @return  输入
     */
    InputStream getInput();

    /**
     * 获取输出
     * @return  获取输出
     */
    OutputStream getOutput();

    /**
     * 获取连接管理器
     * @return  连接管理器
     */
    ConnectionManager getManager();

    /**
     * 获取连接元数据
     * @return ConnectMetadata
     */
    ConnectMetadata getMetadata();

    /**
     * 获取连接会话
     * @return  连接会话
     */
    ConnectionSession getSession();

    /**
     * 是否可用
     * @return  是否可用
     */
    boolean isAvailable();
}
