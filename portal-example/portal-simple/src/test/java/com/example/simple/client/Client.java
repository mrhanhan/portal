package com.example.simple.client;

import com.example.simple.model.User;
import com.portal.core.connect.Connection;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.protocol.JsonData;
import com.portal.core.protocol.param.DefaultParamResolve;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.send.DefaultInvokeSend;

/**
 * Client
 *
 * @author Mrhan
 * @date 2021/6/17 19:13
 */
public class Client {
    public static void main(String[] args) throws Exception {
        // 获取连接

        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();

        Connection connection = manager.getConnection(SocketConnectMetadata.createSocketMetadata("localhost", 1720));

        DefaultInvokeSend defaultInvokeSend = new DefaultInvokeSend();

        DefaultParamResolve resolve = new DefaultParamResolve();

        JsonData data = new JsonData();
        data.setService("userService");
        data.setServiceId("login");
        data.setParamArray(new Param[]{
                resolve.resolve(new User().setUsername("admin").setPassword("123456"), connection.getSession().getServiceContainer())
        });
        long time = System.currentTimeMillis();
        System.out.println(time);
        defaultInvokeSend.invokeSend(data, connection, (param -> {
            User user = resolve.resolve(param, User.class);
            System.out.println("响应：" + user);
            System.out.println(System.currentTimeMillis() - time);
            try {
                connection.close();
                defaultInvokeSend.close();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }
}
