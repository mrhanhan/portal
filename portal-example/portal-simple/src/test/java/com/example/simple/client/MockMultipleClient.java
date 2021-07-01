package com.example.simple.client;

import com.example.simple.model.User;
import com.portal.core.connect.Connection;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.discovery.ProxyInvokeSend;
import com.portal.core.protocol.JsonData;
import com.portal.core.protocol.param.DefaultParamResolve;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.send.DefaultInvokeSend;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * Client
 *
 * @author Mrhan
 * @date 2021/6/17 19:13
 */
public class MockMultipleClient {

    @SneakyThrows
    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                try {
                    run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.join();
        }
    }

    public static void run() throws Exception {
        // 获取连接

        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();

        Connection connection = manager.getConnection(SocketConnectMetadata.createSocketMetadata("localhost", 1720));

        ProxyInvokeSend proxyInvokeSend = new ProxyInvokeSend();
        DefaultParamResolve resolve = new DefaultParamResolve(proxyInvokeSend);
        DefaultInvokeSend defaultInvokeSend = new DefaultInvokeSend(resolve);

        JsonData data = new JsonData();
        data.setService("userService");
        data.setServiceId("login");
        data.setParamArray(new Param[]{
                resolve.resolve(new User().setUsername("admin").setPassword("123456"), connection.getSession().getServiceContainer())
        });
        data.setConnection(connection);
        int c[] = new int[]{90};
        for (int i = 0; i < c[0]; i++) {
            JsonData data1 = new JsonData();
            data1.setService("userService");
            data1.setServiceId("login");
            data1.setParamArray(data.getParamArray());
            data1.setConnection(connection);
            long time = System.currentTimeMillis();
            defaultInvokeSend.invokeSend(data1, connection, (param -> {
                System.out.println("耗时:" + (System.currentTimeMillis() - time));
                User user = resolve.resolve(data1, param, User.class);
                System.out.println("响应：" + user);
            }));
            Thread.sleep(100);
        }
        Thread.sleep(10000);
        long time1 = System.currentTimeMillis();
        defaultInvokeSend.invokeSend(data, connection, (param -> {
            User user = resolve.resolve(data, param, User.class);
            System.out.println("响应：" + user);
            System.out.println(System.currentTimeMillis() - time1);
            try {
                connection.close();
                defaultInvokeSend.close();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        proxyInvokeSend.close();
    }
}
