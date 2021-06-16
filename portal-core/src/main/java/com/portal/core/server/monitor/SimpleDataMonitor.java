package com.portal.core.server.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.server.AbstractServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * IConnectionMonitor
 * 连接检测，检测到写入请求就进行读取
 * @author Mrhan
 * @date 2021/6/15 17:47
 */
@RequiredArgsConstructor
public class SimpleDataMonitor implements DataMonitor {

    @Getter
    public final Connection connection;
    private final AbstractServer server;
    private final AtomicBoolean status = new AtomicBoolean(false);

    @Override
    public void run() {
        InputStream input = connection.getInput();
        OutputStream output = connection.getOutput();
        byte[] data = new byte[512];
        int length = 0;
        try {
            ByteArrayOutputStream cache = new ByteArrayOutputStream();
            //这里的是需要判断是否瞒著于
            while (status.get() && connection.isAvailable()) {
                length = input.read(data);
                cache.write(data, 0, length);
                if (length < data.length) {
                    byte[] bytes = cache.toByteArray();
                    cache.reset();
                    // 调用请求
                    byte[] result = server.onCall(this, bytes);
                    // 写入数据
                    output.write(result);
                    output.flush();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            status.compareAndSet(true, false);
            // 移除 检测
            server.removeDataMonitor(this);
        }

    }

    @Override
    public void close() throws Exception {
        status.set(false);
    }
}
