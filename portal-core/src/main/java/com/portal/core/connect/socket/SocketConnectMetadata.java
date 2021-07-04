package com.portal.core.connect.socket;

import com.portal.core.connect.ConnectMetadata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * SocketConnectMetadata
 * SocketConnectMeta
 * @author Mrhan
 * @date 2021/6/15 16:44
 */
@Getter
@Setter(value = AccessLevel.MODULE)
public class SocketConnectMetadata implements ConnectMetadata {


    private SocketConnectMetadata() {

    }
    @Getter
    @Setter(value = AccessLevel.MODULE)
    public static class ClientSocketConnectMetadata extends SocketConnectMetadata {
        /**
         * 连接HostName
         */
        private String hostname;
        /**
         * 连接端口
         */
        private int port;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientSocketConnectMetadata that = (ClientSocketConnectMetadata) o;
            return port == that.port && Objects.equals(hostname, that.hostname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hostname, port);
        }
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
    public static ClientSocketConnectMetadata createSocketMetadata(String hostname, int port) {
        ClientSocketConnectMetadata socketConnectMetadata = new ClientSocketConnectMetadata();
        socketConnectMetadata.hostname = hostname;
        socketConnectMetadata.port = port;
        return socketConnectMetadata;
    }
}
