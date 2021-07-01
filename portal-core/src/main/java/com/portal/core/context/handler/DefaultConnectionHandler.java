package com.portal.core.context.handler;

import com.portal.core.connect.Connection;
import com.portal.core.context.ConnectionHandler;
import com.portal.core.context.MonitorManager;
import com.portal.core.context.monitor.DefaultDataMonitor;
import com.portal.core.server.DataHandler;
import lombok.RequiredArgsConstructor;

/**
 * DefaultConnectionHandler
 *
 * @author Mrhan
 * @date 2021/7/1 16:31
 */
@RequiredArgsConstructor
public class DefaultConnectionHandler implements ConnectionHandler {
    /**
     * 数据处理程序
     */
    private final DataHandler dataHandler;
    private final MonitorManager monitorManager;
    @Override
    public void onConnection(Connection connection) {
        monitorManager.removeMonitor(new DefaultDataMonitor(connection, dataHandler));
    }
}
