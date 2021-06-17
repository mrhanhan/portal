package com.portal.core.server;

import com.alibaba.fastjson.JSON;
import com.portal.core.protocol.JsonData;
import com.portal.core.protocol.JsonProtocol;
import com.portal.core.utils.ByteCache;
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
public class JsonDataClient {

    public Socket socket;
    private final int port;
    public JsonDataClient(int port) {
        this.port = port;
    }

    @SneakyThrows
    public String call(String serviceName) {
        socket = new Socket();
        socket.connect(new InetSocketAddress(port));
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        JsonData jsonData = new JsonData();
        jsonData.setService(serviceName);

        ByteCache  cache1 = new ByteCache();
        cache1.write(JsonProtocol.START);
        cache1.write(JSON.toJSONString(jsonData).getBytes(StandardCharsets.UTF_8));
        output.write(cache1.toByteArray());
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
