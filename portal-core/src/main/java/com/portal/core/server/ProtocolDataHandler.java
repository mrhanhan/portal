package com.portal.core.server;

import com.portal.core.protocol.Protocol;
import com.portal.core.server.monitor.DataMonitor;

/**
 * ProtocolDataHandler
 *
 * @author Mrhan
 * @date 2021/6/15 20:28
 */
public interface ProtocolDataHandler<T extends Data<?>>  {
    /**
     * 获取协议信息
     * @return Protocol
     */
    Protocol<T> getProtocol();
    /**
     * 序列化
     * @param dataMonitor 数据检测对象
     * @param data byte
     * @return Data
     */
    T serial(DataMonitor dataMonitor, byte[] data);
    /**
     * 序列化
     * @param data Data
     * @return byte
     */
    byte[] deSerial(T data);
    /**
     * 是否支持Data序列化
     * @param bytes  序列化
     * @return      序列化
     */
    boolean isSupport(byte[] bytes);

    /**
     * 是否支持Data反序列化
     * @param data  反序列化
     * @return      反序列化
     */
    boolean isSupport(Data<? extends Data<?>> data);
}
