package com.portal.core.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.portal.core.server.Data;
import com.portal.core.server.ProtocolDataHandler;
import com.portal.core.server.monitor.DataMonitor;
import com.portal.core.utils.ByteVisit;

import java.nio.charset.StandardCharsets;

/**
 * JsonProtocol
 *
 * @author Mrhan
 * @date 2021/6/17 9:33
 */
public class JsonProtocol extends AbstractProtocol<JsonData> implements ProtocolDataHandler<JsonData>{
    public static final byte[]  START = new byte[]{74,83, 79, 78};

    public JsonProtocol() {
        super("json", "1.0.0");
    }

    @Override
    public ProtocolDataHandler<JsonData> getProtocolDataHandler() {
        return this;
    }

    @Override
    public Protocol<JsonData> getProtocol() {
        return this;
    }

    @Override
    public JsonData serial(DataMonitor dataMonitor, byte[] data) {

        JsonData jsonData = JSONObject.parseObject(new String(data, START.length, data.length - START.length), JsonData.class);
        jsonData.setProtocol(this);
        jsonData.setConnection(dataMonitor.getConnection());

        return jsonData;
    }

    @Override
    public byte[] deSerial(JsonData data) {
        String string = JSON.toJSONString(data);
        return ByteVisit.join(START, string.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean isSupport(byte[] bytes) {
        return ByteVisit.equ(bytes, START);
    }

    @Override
    public boolean isSupport(Data data) {
        return data.getClass() == JsonData.class;
    }
}
