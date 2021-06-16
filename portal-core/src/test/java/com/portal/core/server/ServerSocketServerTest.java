package com.portal.core.server;

import com.portal.core.socket1.Client;
import lombok.SneakyThrows;

public class ServerSocketServerTest {

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketPortal server = new ServerSocketPortal();
        server.startUp();
    }

    public void client() {
        SimpleClient client = new SimpleClient(1720);
        System.out.println(client.call("os"));
    }
}