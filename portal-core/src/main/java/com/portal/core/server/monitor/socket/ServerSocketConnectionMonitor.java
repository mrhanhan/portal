package com.portal.core.server.monitor.socket;

import com.portal.core.Portal;
import com.portal.core.connect.socket.ServerSocketConnectionManager;
import com.portal.core.server.monitor.AbstractConnectionMonitor;

/**
 * ServerSocketConnectionMonitor
 * ServerSocketConnection检查项
 * @author Mrhan
 * @date 2021/6/16 11:45
 */
public class ServerSocketConnectionMonitor extends AbstractConnectionMonitor {


    public ServerSocketConnectionMonitor(Portal server) {
        super(server, new ServerSocketConnectionManager(1720));
    }

    public ServerSocketConnectionMonitor(Portal server, int port) {
        super(server, new ServerSocketConnectionManager(port));
    }

}
