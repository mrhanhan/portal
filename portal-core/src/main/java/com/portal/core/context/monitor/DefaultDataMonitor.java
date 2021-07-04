package com.portal.core.context.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.context.DataHandler;
import com.portal.core.context.send.SendData;
import com.portal.core.model.Data;
import com.portal.core.utils.DataReader;
import com.portal.core.utils.DataWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * DefaultDataMonitor
 *
 * @author Mrhan
 * @date 2021/7/1 16:33
 */
@RequiredArgsConstructor
public class DefaultDataMonitor extends AbstractMonitor implements DataMonitor, SendData {

    @Getter
    private final Connection connection;
    private final DataHandler dataHandler;
    private final static int DEFAULT_PACKAGE_SIZE = 128;
    private static final byte[] FLAG = new byte[]{0x2, 0x0, 0x2, 0x1};

    @Override
    public SendData getSendResultData() {
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
        try{
            while (isRunning() & connection.isAvailable()) {
                System.out.println("等待接受数据:");
                Data data = dataReader.readData();
                data.setConnection(connection);
                data.setDataMonitor(this);
                System.out.println("接受数据:" + data);
                dataHandler.onHandler(this, data);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            bis.close();
            input.close();
            close();
        }

    }

    @SneakyThrows
    @Override
    public void send(Data data, Consumer<Data> callback) {
        // 一个连接统一时刻只能有一个线程进行发送数据
        synchronized (connection) {
            if (callback != null) {
                connection.getCallingManager().push(data.getServiceName(), data.getServiceId(), data.getId(), callback);
            }
            OutputStream output = connection.getOutput();
            DataWriter writer  = new DataWriter();
            writer.writeData(data);
            writer.writeTo(output);
            output.flush();
            System.out.println("写入数据：" + data);
        }
    }
}
