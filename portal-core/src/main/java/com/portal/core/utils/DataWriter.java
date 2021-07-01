package com.portal.core.utils;

import com.portal.core.model.Data;
import com.portal.core.model.Param;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * DataWriter
 *
 * @author Mrhan
 * @date 2021/7/1 17:26
 */
public class DataWriter extends ByteCache{


    @SneakyThrows
    public void writeData(Data data) {
        // 写如类型
        this.write((byte)data.getOperate());
        // 写入ID
        this.writeStringAndLength(data.getId());
        // 写入服务名称
        this.writeStringAndLength(data.getServiceName());
        // 写入服务ID
        this.writeStringAndLength(data.getServiceId());
        // 写入参数个数
        Param[] params = data.getParams();
        this.write(ByteVisit.intToBytes(Objects.isNull(params) ? 0 : params.length));
        // 写入参数数据
        if (Objects.nonNull(params)) {
            for (Param param : params) {
                this.writeParam(param);
            }
        }
    }

    /**
     * 写入参数信息
     * @param param 写入参数信息
     */
    @SneakyThrows
    private void writeParam(Param param) {
        // 参数类型 1 byte
        byte type = (byte) param.getType().ordinal();
        write(type);
        // 参数标志 1 byte
        byte flag = 0;
        // 是否是引用
        if (param.isQuote()) {
            flag |= (1 << 2);
        }
        // 是否是异常
        if (param.isException()) {
            flag |= (1 << 1);
        }
        // 是否有字段名称
        if (param.getFiledName() != null) {
            flag |= 1;
        }
        write(flag);
        // 服务长度 4 byte
        if (param.isQuote()) {
            writeStringAndLength(param.getQuoteService());
        }
        // 字段名
        if (param.getFiledName() != null) {
            writeStringAndLength(param.getFiledName());
        }
        // 数据和长度
        if (param.getData() != null) {
            byte[] data = param.getData();
            write(ByteVisit.intToBytes(data.length));
            write(data);
        }
        // 是否有子参数
        Param[] children = param.getChildren();
        write(ByteVisit.intToBytes(Objects.isNull(children) ? 0: children.length));
        if (Objects.nonNull(children)) {
            for (Param child : children) {
                this.writeParam(child);
            }
        }
    }

    @SneakyThrows
    private void writeStringAndLength(String str) {
        int length = 0;
        byte[] data = null;
        if (Objects.nonNull(str)) {
            data = str.getBytes(StandardCharsets.UTF_8);
            length = data.length;
        }
        super.write(ByteVisit.intToBytes(length));

        if(Objects.nonNull(data)) {
            super.write(data);
        }
    }
}
