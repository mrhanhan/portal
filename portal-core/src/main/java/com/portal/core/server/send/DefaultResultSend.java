package com.portal.core.server.send;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.Protocol;
import com.portal.core.server.AbstractPortal;
import com.portal.core.server.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;

/**
 * DefaultResultSend
 *
 * @author Mrhan
 * @date 2021/6/17 16:44
 */
@RequiredArgsConstructor
public class DefaultResultSend implements ResultSend {

    private final AbstractPortal server;

    @Override
    public void resultSend(Data<?> data, Connection connection, Protocol<? extends Data<?>> protocol) {
        // 反序列化
        byte[] bytes = server.deSerial(data);
        OutputStream output = connection.getOutput();
        try {
            output.write(bytes);
            output.flush();
        } catch (IOException e) {
            server.handleException(e);
        }
    }
}
