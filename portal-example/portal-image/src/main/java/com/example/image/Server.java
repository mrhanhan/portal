package com.example.image;

import com.example.image.api.ImageService;
import com.example.image.impl.ImageServiceImpl;
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
 * @date 2021/7/6 10:30
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
        BeanDelegateService service = new BeanDelegateService("imageService", new ImageServiceImpl(this), ImageService.class);
        service.register("image");
        service.register("close");
        context.registerService(service);
    }

    public static void main(String[] args) {
        new Server().startUp();

    }
}
