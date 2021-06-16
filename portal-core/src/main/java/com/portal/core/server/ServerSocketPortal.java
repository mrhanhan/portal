package com.portal.core.server;

import com.portal.core.protocol.Data;
import com.portal.core.server.monitor.socket.ServerSocketConnectionMonitor;

import java.nio.charset.StandardCharsets;
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
}
