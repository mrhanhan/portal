package com.portal.core.connect.socket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * SocketConnectMetadata
 * SocketConnectMeta
 * @author Mrhan
 * @date 2021/6/15 16:44
 */
@Getter
@Setter(value = AccessLevel.MODULE)
public class SocketConnectMetadata {
    /**
     * 连接HostName
     */

    private String hostname;
    /**
     * 连接端口
     */
    private int port;

    private SocketConnectMetadata() {

    }

    public static class ServerSocketConnectMetadata extends SocketConnectMetadata {

    }

    public static ServerSocketConnectMetadata createServerSocket() {
        return new ServerSocketConnectMetadata();
    }

    /**
     * 创建Socket连接 Metadata
     * @param hostname  连接目标Host名称
     * @param port      连接目标端口
     * @return          返回 SocketConnectMetadata
     */
    public static SocketConnectMetadata createSocketMetadata(String hostname, int port) {
        SocketConnectMetadata socketConnectMetadata = new ServerSocketConnectMetadata();
        socketConnectMetadata.hostname = hostname;
        socketConnectMetadata.port = port;
        return socketConnectMetadata;
    }
}
