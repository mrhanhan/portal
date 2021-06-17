package com.portal.core.server;

import com.portal.core.server.invoker.Invoker;
import com.portal.core.server.monitor.socket.ServerSocketConnectionMonitor;

import java.util.List;

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



    @Override
    protected byte[] executeProtocolDataHandlerToByte(List<ProtocolDataHandler<?>> supportList, Data data) {
        return new byte[0];
    }

    @Override
    protected Invoker createInvoker() {
        return null;
    }

}
