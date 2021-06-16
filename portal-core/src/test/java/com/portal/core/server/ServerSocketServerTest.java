package com.portal.core.server;

import lombok.SneakyThrows;

import static org.junit.Assert.*;

public class ServerSocketServerTest {

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketServer server = new ServerSocketServer();
        server.startUp();
    }

}