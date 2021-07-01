package com.portal.core.context.monitor;

import com.portal.core.ExceptionHandler;
import com.portal.core.connect.ConnectionManager;
import com.portal.core.context.ConnectionHandler;
import com.portal.core.context.ConnectionMonitor;
import lombok.RequiredArgsConstructor;

/**
 * AbstractConnectionMonitor
 *
 * @author Mrhan
 * @date 2021/7/1 17:17
 */
@RequiredArgsConstructor
public abstract class AbstractConnectionMonitor extends AbstractMonitor implements ConnectionMonitor {

    private final ExceptionHandler exceptionHandler;
    private final ConnectionHandler handler;
    private final ConnectionManager connectionManager;

    @Override
    public void run() {

    }

    @Override
    public void close() throws Exception {
        connectionManager.close();
    }

    @Override
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
