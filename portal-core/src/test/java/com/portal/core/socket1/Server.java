package com.portal.core.socket1;

import com.portal.core.service.IService;
import com.portal.core.service.IServiceContainer;
import com.portal.core.service.SimpleServiceContainer;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Server
 *
 * @author Mrhan
 * @date 2021/6/10 12:00
 */
public class Server {

    private final int port;
    @Delegate
    private IServiceContainer container;

    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
        container = new SimpleServiceContainer();
    }


    @SneakyThrows
    public void start() {
        // 开启端口
        final ServerSocket serverSocket = new ServerSocket(port);
        this.serverSocket = serverSocket;
        // 监听连接
        Thread t = new Thread(() -> {
            try {
                Socket accept = serverSocket.accept();
                // 处理程序
                Server.this.process(accept);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.join();
    }

    @SneakyThrows
    private void process(Socket accept) {
        InputStream input = accept.getInputStream();
        OutputStream output = accept.getOutputStream();
        ByteArrayOutputStream cache = new ByteArrayOutputStream();
        byte[] data = new byte[512];
        int length;
        while ((length = input.read(data)) > 0) {
            cache.write(data, 0, length);
            if (length < data.length) {
                break;
            }
        }
        String serverName = new String(cache.toByteArray(), StandardCharsets.UTF_8);
        cache.close();
        // 调用服务
        IService service = this.getService(serverName);
        String result = null;
        if (service != null) {
            Object invoke = service.invoke(serverName);
            if (invoke != null) {
                result = invoke.toString();
            } else {
                result = "null";
            }
        } else {
            result = "no service";
        }
        output.write(result.getBytes(StandardCharsets.UTF_8));
        output.close();
        input.close();
        accept.close();
    }
}
