package com.portal.core.server;

import com.portal.core.protocol.Protocol;
import com.portal.core.server.monitor.DataMonitor;

import java.util.List;

/**
 * MultipleProtocolDataHandler
 * 多
 * @author Mrhan
 * @date 2021/6/17 17:23
 */
public class MultipleProtocolDataHandler extends DefaultProtocolDataHandlerRegister implements ProtocolDataHandler<Data<?>>{


    public MultipleProtocolDataHandler() {
    }

    @Override
    public Protocol<Data<?>> getProtocol() {
        return null;
    }

    @Override
    public Data<?> serial(DataMonitor dataMonitor, byte[] data) {
        return executeProtocolDataHandlerToData(dataMonitor, getSupportList(data), data);
    }

    @Override
    public byte[] deSerial(Data<?> data) {
        return executeProtocolDataHandlerToByte(getSupportList(data), data);
    }

    @Override
    public boolean isSupport(byte[] bytes) {
        return !getSupportList(bytes).isEmpty();
    }

    @Override
    public boolean isSupport(Data<? extends Data<?>> data) {
        return !getSupportList(data).isEmpty();
    }


    /**
     * 执行协议数据转换器，转换byte => data
     *
     * @param dataMonitor 数据检查项
     * @param supportList 可以使用的数据转换器
     * @param data        bytes
     * @return 返回转换后的Data
     */
    protected  Data<? extends Data<?>> executeProtocolDataHandlerToData(DataMonitor dataMonitor, List<ProtocolDataHandler<Data<?>>> supportList, byte[] data) {
        if (supportList.isEmpty()) {
            return null;
        }
        ProtocolDataHandler<Data<?>> dataHandler = supportList.get(0);
        return dataHandler.serial(dataMonitor, data);
    }

    /**
     * 执行协议数据转换器，转换byte => data
     *
     * @param supportList 可以使用的数据转换器
     * @param data        bytes
     * @return 返回转换后的Data
     */
    protected  byte[] executeProtocolDataHandlerToByte(List<ProtocolDataHandler<Data<?>>> supportList, Data<?> data) {
        if (supportList.isEmpty()) {
            return null;
        }
        ProtocolDataHandler<Data<?>> dataHandler = supportList.get(0);
        return dataHandler.deSerial(data);
    }
}
