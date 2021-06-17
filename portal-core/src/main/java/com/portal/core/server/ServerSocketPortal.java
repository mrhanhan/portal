package com.portal.core.server;

import com.portal.core.server.monitor.socket.ServerSocketConnectionMonitor;

/**
 * ServerSocketServer
 *
 * @author Mrhan
 * @date 2021/6/16 11:50
 */
public class ServerSocketPortal extends AbstractPortal {


    @Override
    protected ConnectionMonitor createConnectionMonitor() {
        return new ServerSocketConnectionMonitor(this);
    }


}
