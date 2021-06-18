package com.portal.core.server.send;

import com.portal.core.ExceptionHandler;
import com.portal.core.connect.Connection;
import com.portal.core.server.Data;
import com.portal.core.server.ProtocolDataHandler;
import com.portal.core.server.monitor.DataMonitor;
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

    private final ProtocolDataHandler<Data<?>> protocolDataHandler;
    private final ExceptionHandler exceptionHandler;
    @Override
    public void resultSend(Data<?> data, Connection connection, DataMonitor monitor) {
        // 反序列化
        byte[] bytes = protocolDataHandler.deSerial(data);
        OutputStream output = connection.getOutput();
        try {
            output.write(monitor.bale(bytes));
            output.flush();
        } catch (IOException e) {
            exceptionHandler.onException(e);
        }
    }
}
