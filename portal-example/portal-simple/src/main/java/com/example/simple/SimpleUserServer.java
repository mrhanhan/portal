package com.example.simple;

import com.example.simple.model.User;
import com.example.simple.service.AccountService;
import com.example.simple.service.UserService;
import com.example.simple.service.impl.UserServiceImpl;
import com.portal.core.connect.socket.ClientSocketConnectionManager;
import com.portal.core.connect.socket.SocketConnectMetadata;
import com.portal.core.context.AbstractPortalContext;
import com.portal.core.context.ConnectionHandler;
import com.portal.core.context.ConnectionMonitor;
import com.portal.core.context.PortalContext;
import com.portal.core.context.PortalLifeCycle;
import com.portal.core.context.monitor.socket.ServerSocketConnectionMonitor;
import com.portal.core.discovery.DefaultServiceDiscovery;
import com.portal.core.service.BeanDelegateService;
import lombok.SneakyThrows;

/**
 * SimpleExampleServer
 *
 * @author Mrhan
 * @date 2021/6/17 19:00
 */
public class SimpleUserServer extends AbstractPortalContext {

    @Override
    protected ConnectionMonitor getConnectionMonitor(ConnectionHandler connectionHandler) {
        return new ServerSocketConnectionMonitor(this, connectionHandler, 1720);
    }


    @SneakyThrows
    @Override
    public void onStartup(PortalContext context) {
        super.onStartup(context);
        ClientSocketConnectionManager manager = new ClientSocketConnectionManager();
        DefaultServiceDiscovery discovery = new DefaultServiceDiscovery(manager, (c) -> SocketConnectMetadata.createSocketMetadata("localhost", 1721));

        UserServiceImpl userService = new UserServiceImpl();
        userService.setAccountService(discovery.getService("accountService", AccountService.class));

        // 注册服务
        BeanDelegateService service = new BeanDelegateService("userService", userService, UserService.class);
        service.register("login", User.class);
        service.register("getUserInfo", Long.class);
        service.register("test");
        service.register("file");
        service.register("output");
        service.register("printOutput");
        context.registerService(service);
        context.registerLifeCycle(new PortalLifeCycle() {
            @Override
            public void onInitialize(PortalContext context) {

            }

            @Override
            public void onStartup(PortalContext context) {

            }

            @Override
            public void onShutDown(PortalContext context) {
                discovery.close();
            }
        });
    }

    public static void main(String[] args) {
        try {
            new SimpleUserServer().startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
