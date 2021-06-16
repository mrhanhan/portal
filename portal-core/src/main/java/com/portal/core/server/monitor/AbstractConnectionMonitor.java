package com.portal.core.server.monitor;

import com.portal.core.Server;
import com.portal.core.connect.Connection;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.server.ConnectionMonitor;
import lombok.RequiredArgsConstructor;

/**
 * AbstractConnectionMonitor
 *
 * @author Mrhan
 * @date 2021/6/16 11:46
 */
@RequiredArgsConstructor
public abstract class AbstractConnectionMonitor implements ConnectionMonitor {

    public volatile boolean status = false;

    private final Server server;

    @Override
    public void close() throws Exception {
        status = false;
        getConnectionManager().close();
    }

    @Override
    public void run() {
        status = true;
        while (status) {
            try {
                Connection connection = getConnectionManager().getConnection(SocketConnectMetadata.createServerSocket());
                server.onHandler(connection);
            } catch (InterruptedException e) {
                try {
                    close();
                } catch (Exception exception) {
                    server.handleException(exception);
                }
                break;
            } catch (Exception e) {
                server.handleException(e);
            }
        }
    }
}
