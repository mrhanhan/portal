package com.portal.core.connect.socket;

import junit.framework.TestCase;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

public class ServerSocketConnectionManagerTest{

    public ServerSocketConnectionManager manager;

    @Before
    public void startUpBefore() {
        manager = new ServerSocketConnectionManager(1720);
    }

    @SneakyThrows
    @Test
    public void startUpServer() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Client: " + manager.getConnection(SocketConnectMetadata.createServerSocket()).toString());
        }
    }

}