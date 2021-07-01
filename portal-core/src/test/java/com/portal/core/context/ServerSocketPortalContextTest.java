package com.portal.core.context;

import com.portal.core.context.monitor.socket.ServerSocketConnectionMonitor;

public class ServerSocketPortalContextTest extends AbstractPortalContext{

    @Override
    protected ConnectionMonitor getConnectionMonitor(ConnectionHandler connectionHandler) {
        return new ServerSocketConnectionMonitor(this, connectionHandler, 1720);
    }

    public static void main(String[] args) {
        new ServerSocketPortalContextTest().startUp();
    }
}