package com.portal.core.context;

import com.portal.core.model.Data;
import com.portal.core.utils.ByteCache;
import com.portal.core.utils.ByteVisit;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

/**
 * DefaultDataSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 17:19
 */
public class DefaultDataSerialization implements DataSerialization, BytesSerialization{

    public static final byte[] EMP_DATA = new byte[]{};
    @SneakyThrows
    @Override
    public byte[] serial(Data data) {
        ByteCache cache = new ByteCache();
        // 参数个数
        int operate = data.getOperate();
        // 写入类型
        cache.write((byte)operate);
        // 写入服务名称
        byte[] idBytes = data.getId().getBytes(StandardCharsets.UTF_8);
        byte[] serviceBytes = data.getServiceName().getBytes(StandardCharsets.UTF_8);
        byte[] serviceIdBytes = data.getServiceId().getBytes(StandardCharsets.UTF_8);
        // 写入ID 长度
        cache.write(ByteVisit.intToBytes(idBytes.length));
        // 写入ID数据
        cache.write(idBytes);
        // 写入服务名称长度
        cache.write(ByteVisit.intToBytes(idBytes.length));

        return cache.toByteArray();
    }


    @Override
    public Data serial(byte[] bytes) {
        return null;
    }

}
