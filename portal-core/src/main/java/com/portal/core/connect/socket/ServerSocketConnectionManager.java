package com.portal.core.connect.socket;

import com.portal.core.connect.AbstractCacheConnectionManager;
import com.portal.core.connect.ConnectMetadata;
import com.portal.core.connect.IConnection;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * ServerSocketConnectionManager
 *
 * @author Mrhan
 * @date 2021/6/15 16:50
 */
public class ServerSocketConnectionManager extends AbstractCacheConnectionManager {

    private final ServerSocket socket;


    @SneakyThrows
    public ServerSocketConnectionManager(String hostName, int port) {
        socket = new ServerSocket();
        if (hostName != null) {
            socket.bind(new InetSocketAddress(hostName, port));
        } else {
            socket.bind(new InetSocketAddress(port));
        }
    }

    public ServerSocketConnectionManager(int port) {
        this(null, port);
    }


    @Override
    public boolean isSupport(ConnectMetadata metadata) {
        return SocketConnectMetadata.ServerSocketConnectMetadata.class.isAssignableFrom(metadata.getClass());
    }

    @Override
    protected IConnection createConnection(ConnectMetadata metadata) throws IOException {
        SocketConnectMetadata.ServerSocketConnectMetadata serverSocketMetadata = (SocketConnectMetadata.ServerSocketConnectMetadata) metadata;
        return new SocketConnect(socket.accept());
    }

    @Override
    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
