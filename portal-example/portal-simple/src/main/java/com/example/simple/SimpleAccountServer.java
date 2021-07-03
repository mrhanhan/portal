package com.example.simple;

import com.example.simple.model.User;
import com.example.simple.service.AccountService;
import com.example.simple.service.impl.AccountServiceImpl;
import com.portal.core.context.AbstractPortalContext;
import com.portal.core.context.ConnectionHandler;
import com.portal.core.context.ConnectionMonitor;
import com.portal.core.context.PortalContext;
import com.portal.core.context.monitor.socket.ServerSocketConnectionMonitor;
import com.portal.core.service.BeanDelegateService;
import lombok.SneakyThrows;

/**
 * SimpleExampleServer
 *
 * @author Mrhan
 * @date 2021/6/17 19:00
 */
public class SimpleAccountServer extends AbstractPortalContext {


    @SneakyThrows
    @Override
    public void onStartup(PortalContext context) {
        super.onStartup(context);
        // 注册服务
        BeanDelegateService service = new BeanDelegateService("accountService", new AccountServiceImpl(), AccountService.class);
        service.register("createAccount", User.class);
        context.registerService(service);
    }

    public static void main(String[] args) {
        try {
            new SimpleAccountServer().startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ConnectionMonitor getConnectionMonitor(ConnectionHandler connectionHandler) {
        return new ServerSocketConnectionMonitor(this, connectionHandler, 1720);
    }
}
