package com.portal.core.connect.socket;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

public class ClientSocketConnectionManagerTest {
    public ClientSocketConnectionManager manager;

    @Before
    public void startUpBefore() {
        manager = new ClientSocketConnectionManager();
    }

    @SneakyThrows
    @Test
    public void startUpServer() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Server: " + manager.getConnection(SocketConnectMetadata.createSocketMetadata(null, 1720)).toString());
        }
    }

}