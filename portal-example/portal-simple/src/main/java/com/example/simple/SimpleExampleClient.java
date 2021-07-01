//package com.example.simple;
//
//import com.example.simple.model.NoSerial;
//import com.example.simple.model.User;
//import com.example.simple.service.UserService;
//import com.portal.core.connect.socket.ClientSocketConnectionManager;
//import com.portal.core.connect.socket.SocketConnectMetadata;
//import com.portal.core.discovery.DefaultServiceDiscovery;
//import com.portal.core.discovery.ServiceDiscovery;
//
//import java.io.File;
//import java.io.OutputStream;
//import java.nio.charset.StandardCharsets;
//
///**
// * SimpleExampleClient
// *
// * @author Mrhan
// * @date 2021/6/30 17:00
// */
//public class SimpleExampleClient {
//
//    public static void main(String[] args) throws Exception {
//        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
//        ServiceDiscovery discovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1720));
//        UserService userService = discovery.getService("userService", UserService.class);
//        User userInfo = userService.getUserInfo(1L);
//        System.out.println(userInfo);
//        // 获取非序列化对象
//        NoSerial noSerial = userService.test();
//        // 做添加操作
//        noSerial.add();
//        noSerial.add();
//        System.out.println(noSerial.getCount());
//        // file
//        File file = userService.file();
//        System.out.println(file.getName());
//        // output
//        OutputStream outputStream = userService.output();
//        outputStream.write("Hello World Portal".getBytes(StandardCharsets.UTF_8));
//        System.out.println(userService.printOutput());
//
//        discovery.close();
//        System.exit(0);
//    }
//}
