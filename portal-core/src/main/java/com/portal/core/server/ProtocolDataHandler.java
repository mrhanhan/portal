package com.portal.core.server;

import com.portal.core.Support;
import com.portal.core.protocol.Data;

/**
 * ProtocolDataHandler
 *
 * @author Mrhan
 * @date 2021/6/15 20:28
 */
public interface ProtocolDataHandler<T extends Data> extends Support<byte[]> {
    /**
     * 序列化
     * @param data byte
     * @return Data
     */
    T serial(byte[] data);
    /**
     * 序列化
     * @param data Data
     * @return byte
     */
    byte[] deSerial(Data data);
}
