package com.portal.core.server.send;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.Protocol;
import com.portal.core.server.Data;
import com.portal.core.server.ProtocolDataHandler;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * DefaultResultSend
 *
 * @author Mrhan
 * @date 2021/6/17 16:44
 */
@RequiredArgsConstructor
public class DefaultResultSend implements ResultSend {

    private final ProtocolDataHandler<Data<?>> protocolDataHandler;
    private final Consumer<Exception> exceptionHandler;
    @Override
    public void resultSend(Data<?> data, Connection connection, Protocol<? extends Data<?>> protocol) {
        // 反序列化
        byte[] bytes = protocolDataHandler.deSerial(data);
        OutputStream output = connection.getOutput();
        try {
            output.write(bytes);
            output.flush();
        } catch (IOException e) {
            exceptionHandler.accept(e);
        }
    }
}
