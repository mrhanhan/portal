package com.example.simple;

import com.example.simple.service.UserService;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.discovery.DefaultServiceDiscovery;
import com.portal.core.discovery.ServiceDiscovery;

/**
 * SimpleExampleClient
 *
 * @author Mrhan
 * @date 2021/6/30 17:00
 */
public class SimpleExampleClient {

    public static void main(String[] args) throws Exception {
        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
        ServiceDiscovery discovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1720));
        UserService userService = discovery.getService("userService", UserService.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(userService.getUserInfo((long) i));
        }
        discovery.close();
        System.exit(0);
    }
}
