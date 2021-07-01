package com.portal.core.context.monitor.socket;

import com.portal.core.connect.socket.ServerSocketConnectionManager;
import com.portal.core.context.ConnectionHandler;
import com.portal.core.context.PortalContext;
import com.portal.core.context.monitor.AbstractConnectionMonitor;

/**
 * ServerSocketConnectionMonitor
 * ServerSocketConnection检查项
 * @author Mrhan
 * @date 2021/6/16 11:45
 */
public class ServerSocketConnectionMonitor extends AbstractConnectionMonitor {

    public ServerSocketConnectionMonitor(PortalContext context, ConnectionHandler handler, int port) {
        super(context, handler, new ServerSocketConnectionManager(port));
    }

}
