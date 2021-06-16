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
public class ServerSocketServer extends AbstractServer{


    @Override
    protected ConnectionMonitor createConnectionMonitor() {
        return new ServerSocketConnectionMonitor(this);
    }

    @Override
    protected Data executeProtocolDataHandlerToData(List<ProtocolDataHandler<?>> supportList, byte[] data) {
        System.out.println(new String(data, StandardCharsets.UTF_8));
        return null;
    }

    @Override
    protected byte[] executeProtocolDataHandlerToByte(List<ProtocolDataHandler<?>> supportList, Data data) {
        return new byte[0];
    }
}
