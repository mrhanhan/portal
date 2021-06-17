package com.portal.core.server;

import lombok.SneakyThrows;
import org.junit.Test;

public class ServerSocketServerTest {

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketPortal server = new ServerSocketPortal();
        server.startUp();
    }

    @Test
    public void client() {
        SimpleClient client = new SimpleClient(1720);
        System.out.println(client.call("os"));
    }
    @Test
    public void jsonClient() {
        JsonDataClient client = new JsonDataClient(1720);
        System.out.println(client.call("json"));
    }
}