package com.portal.core.socket1;

import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Client
 *
 * @author Mrhan
 * @date 2021/6/15 16:23
 */
public class Client {

    public Socket socket;
    private final int port;
    public Client(int port) {
        this.port = port;
    }

    @SneakyThrows
    public String call(String serviceName) {
        socket = new Socket();
        socket.connect(new InetSocketAddress(port));
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        output.write(serviceName.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream cache = new ByteArrayOutputStream();
        byte[] data = new byte[512];
        int length;
        while ((length = input.read(data)) > 0) {
            cache.write(data, 0, length);
            if (length < data.length) {
                break;
            }
        }
        String result = new String(cache.toByteArray(), StandardCharsets.UTF_8);
        output.close();
        input.close();
        socket.close();
        return result;
    }
}
