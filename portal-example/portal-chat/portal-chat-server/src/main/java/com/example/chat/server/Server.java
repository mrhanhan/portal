package com.example.chat.server;

import com.example.chat.api.RegisterClientService;
import com.example.chat.api.SendMessageService;
import com.example.chat.model.Message;
import com.example.chat.model.MessageCallback;
import com.example.chat.model.UserInfo;
import com.example.chat.server.impl.ServerServiceImpl;
import com.portal.core.context.AbstractPortalContext;
import com.portal.core.context.ConnectionHandler;
import com.portal.core.context.ConnectionMonitor;
import com.portal.core.context.PortalContext;
import com.portal.core.context.monitor.socket.ServerSocketConnectionMonitor;
import com.portal.core.service.BeanDelegateService;
import lombok.SneakyThrows;

/**
 * Server
 *
 * @author Mrhan
 * @date 2021/7/4 12:45
 */
public class Server extends AbstractPortalContext {

    @Override
    protected ConnectionMonitor getConnectionMonitor(ConnectionHandler connectionHandler) {
        return new ServerSocketConnectionMonitor(this, connectionHandler, 1720);
    }

    @SneakyThrows
    @Override
    public void onStartup(PortalContext context) {
        super.onStartup(context);
        ServerServiceImpl serverService = new ServerServiceImpl();
        BeanDelegateService service = new BeanDelegateService("registerClientService", serverService, RegisterClientService.class);
        service.register("register", UserInfo.class, MessageCallback.class);
        context.registerService(service);

        service = new BeanDelegateService("sendMessageService", serverService, SendMessageService.class);
        service.register("send", Message.class);
        context.registerService(service);
    }

    public static void main(String[] args) {
        new Server().startUp();
    }
}
