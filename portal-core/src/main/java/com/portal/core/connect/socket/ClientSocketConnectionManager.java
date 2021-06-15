package com.portal.core.connect.socket;

import com.portal.core.connect.AbstractCacheConnectionManager;
import com.portal.core.connect.ConnectMetadata;
import com.portal.core.connect.IConnection;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * ClientSocketConnectionManager
 *
 * @author Mrhan
 * @date 2021/6/15 17:09
 */
public class ClientSocketConnectionManager extends AbstractCacheConnectionManager {
    @Override
    public boolean isSupport(ConnectMetadata metadata) {
        return SocketConnectMetadata.ClientSocketConnectMetadata.class.isAssignableFrom(metadata.getClass());
    }

    @Override
    protected IConnection createConnection(ConnectMetadata metadata) throws Exception {
        SocketConnectMetadata.ClientSocketConnectMetadata connectMetadata = (SocketConnectMetadata.ClientSocketConnectMetadata) metadata;
        Socket socket = new Socket();
        if (connectMetadata.getHostname() != null) {
            socket.connect(new InetSocketAddress(connectMetadata.getHostname(), connectMetadata.getPort()));
        } else {
            socket.connect(new InetSocketAddress(connectMetadata.getPort()));
        }
        return new SocketConnect(socket);
    }
}
