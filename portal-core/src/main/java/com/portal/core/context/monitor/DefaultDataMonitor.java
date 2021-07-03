package com.portal.core.context.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.context.DataHandler;
import com.portal.core.context.send.SendResultData;
import com.portal.core.model.Data;
import com.portal.core.utils.DataReader;
import com.portal.core.utils.DataWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * DefaultDataMonitor
 *
 * @author Mrhan
 * @date 2021/7/1 16:33
 */
@RequiredArgsConstructor
public class DefaultDataMonitor extends AbstractMonitor implements DataMonitor, SendResultData{

    @Getter
    private final Connection connection;
    private final DataHandler dataHandler;
    private final static int DEFAULT_PACKAGE_SIZE = 128;
    private static final byte[] FLAG = new byte[]{0x2, 0x0, 0x2, 0x1};

    @Override
    public SendResultData getSendResultData() {
        return this;
    }

    @Override
    public void close() throws Exception {
        end();

    }

    @SneakyThrows
    @Override
    public void run() {
        begin();
        InputStream input = connection.getInput();
        BufferedInputStream bis = new BufferedInputStream(input);
        DataReader dataReader = new DataReader(bis);
        byte[] flag = new byte[4];
        try{
            while (isRunning() & connection.isAvailable()) {
                Data data = dataReader.readData();
                dataHandler.onHandler(this, data);
            }
        }finally {
            bis.close();
            input.close();
            close();
        }

    }

    @SneakyThrows
    @Override
    public void send(Data data) {
        // 一个连接统一时刻只能有一个线程进行发送数据
        synchronized (connection) {
            OutputStream output = connection.getOutput();
            DataWriter writer  = new DataWriter();
            writer.writeData(data);
            writer.writeTo(output);
            output.flush();
        }
    }
}
