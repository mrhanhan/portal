package com.portal.core.discovery;

import com.portal.core.connect.ConnectMetadata;
import com.portal.core.connect.Connection;
import com.portal.core.connect.ConnectionManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.function.Function;

/**
 * DefaultServiceDiscovery
 *
 * @author Mrhan
 * @date 2021/6/30 16:30
 */
@RequiredArgsConstructor
public class DefaultServiceDiscovery extends AbstractServiceDiscovery {


    private final ConnectionManager connectionManager;

    private final Function<ConnectionManager, ConnectMetadata> getMetadata;

    @SneakyThrows
    @Override
    protected Connection getConnection() {
        return connectionManager.getConnection(getMetadata.apply(connectionManager));
    }

    @SneakyThrows
    @Override
    public void close() {
        super.close();
        connectionManager.close();
    }
}
