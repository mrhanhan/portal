package com.portal.core.context.monitor;

import com.portal.core.connect.Connection;
import com.portal.core.context.DataHandler;
import com.portal.core.context.send.SendResultData;
import com.portal.core.model.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @Override
    public SendResultData getSendResultData() {
        return this;
    }

    @Override
    public void close() throws Exception {
        end();
    }

    @Override
    public void run() {

    }

    @Override
    public void send(Data data) {

    }
}
