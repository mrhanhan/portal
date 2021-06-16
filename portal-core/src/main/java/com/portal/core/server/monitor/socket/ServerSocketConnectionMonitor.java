package com.portal.core.server.monitor.socket;

import com.portal.core.Server;
import com.portal.core.connect.ConnectionManager;
import com.portal.core.connect.socket.ServerSocketConnectionManager;
import com.portal.core.server.monitor.AbstractConnectionMonitor;

/**
 * ServerSocketConnectionMonitor
 * ServerSocketConnection检查项
 * @author Mrhan
 * @date 2021/6/16 11:45
 */
public class ServerSocketConnectionMonitor extends AbstractConnectionMonitor {


    public ServerSocketConnectionMonitor(Server server) {
        super(server);
    }

    @Override
    public ConnectionManager getConnectionManager() {
        return new ServerSocketConnectionManager(1720);
    }
}
