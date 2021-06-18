package com.portal.core.server.monitor;

import com.portal.core.Portal;
import com.portal.core.connect.Connection;
import com.portal.core.connect.ConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.server.ConnectionMonitor;
import lombok.Getter;
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

    private final Portal server;
    @Getter
    private final ConnectionManager connectionManager;

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
                    server.onException(exception);
                }
                break;
            } catch (Exception e) {
                server.onException(e);
            }
        }
    }
}
