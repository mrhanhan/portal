package com.example.simple;

import com.example.simple.model.NoSerial;
import com.example.simple.model.User;
import com.example.simple.service.UserService;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.discovery.DefaultServiceDiscovery;

/**
 * SimpleExampleClient
 *
 * @author Mrhan
 * @date 2021/6/30 17:00
 */
public class SimpleExampleClient {

    public static void main(String[] args) throws Exception {
        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
        DefaultServiceDiscovery discovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1720));
        UserService userService = discovery.getService("userService", UserService.class);
        long time = System.currentTimeMillis();
        User userInfo = userService.getUserInfo(1L);
        System.out.println(userInfo);
        System.out.println("time:" + (System.currentTimeMillis() - time));
        // 获取非序列化对象
        NoSerial noSerial = userService.test();
        // 做添加操作
        time = System.currentTimeMillis();
        noSerial.add();
        noSerial.add();
        System.out.println("time:" + (System.currentTimeMillis() - time));
        // 做添加操作
        time = System.currentTimeMillis();
        noSerial.add();
        noSerial.add();
        System.out.println("time:" + (System.currentTimeMillis() - time));
        // 做添加操作
        time = System.currentTimeMillis();
        noSerial.add();
        noSerial.add();
        System.out.println("time:" + (System.currentTimeMillis() - time));

        System.out.println(noSerial.getCount());
        discovery.close();
    }
}
