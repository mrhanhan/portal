package com.portal.core.protocol;

import com.portal.core.server.ProtocolDataHandler;
import com.portal.core.server.monitor.DataMonitor;
import com.portal.core.utils.ByteCache;
import com.portal.core.utils.ByteVisit;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

/**
 * JsonData
 *
 * @author Mrhan
 * @date 2021/6/15 17:44
 */
public class SimpleTextProtocol extends AbstractProtocol<SimpleTextData> implements ProtocolDataHandler<SimpleTextData>{

    public static final byte[]  START = new byte[]{115, 105, 109, 112, 108, 101};

    public SimpleTextProtocol() {
        super("simple", "1.0.0");
    }

    @Override
    public ProtocolDataHandler<SimpleTextData> getProtocolDataHandler() {
        return this;
    }

    @Override
    public boolean isSupport(byte[] bytes) {
        return ByteVisit.equ(bytes, START);
    }

    @Override
    public boolean isSupport(Data data) {
        return data instanceof SimpleTextData;
    }

    @Override
    public Protocol<SimpleTextData> getProtocol() {
        return this;
    }

    @Override
    public SimpleTextData serial(DataMonitor dataMonitor, byte[] data) {
        SimpleTextData simpleTextData = new SimpleTextData();
        simpleTextData.setProtocol(this);
        simpleTextData.setService(new String(data, START.length, data.length, StandardCharsets.UTF_8));
        simpleTextData.setConnection(dataMonitor.getConnection());

        return simpleTextData;
    }

    @SneakyThrows
    @Override
    public byte[] deSerial(SimpleTextData data) {
        // 序列化
        ByteCache cache = new ByteCache();
        cache.write(START);
        cache.writeString(data.getService());
        return cache.toByteArray();
    }
}
