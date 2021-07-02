package com.portal.core.utils;

import com.portal.core.model.Data;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * DataReader
 * DataReader
 * @author Mrhan
 * @date 2021/7/1 17:44
 */
@RequiredArgsConstructor
public class DataReader {

    private final InputStream inputStream;

    public DataReader(byte[] bytes) {
        this(new ByteArrayInputStream(bytes));
    }

    /**
     * 读取数据
     * @return  返回读取到的Data
     */
    @SneakyThrows
    public Data readData() {
        Data data = new Data();
        // 读取类型
        data.setOperate(inputStream.read());
        // 读取ID
        data.setId(readString());
        // 服务名称
        data.setServiceName(readString());
        // 服务ID
        data.setServiceId(readString());
        // 读取参数个数
        int length = readInt();
        Param[] params = new Param[length];
        for (int i = 0; i < length; i++) {
            params[i] = readParam();
        }
        data.setParams(params);
        return data;
    }

    /**
     * 读取Param
     * @return  返回Param
     */
    @SneakyThrows
    private Param readParam() {
        Param param = new Param();
        // 读取类型
        int typeIndex = inputStream.read();
        param.setType(ParamTypeEnum.values()[typeIndex]);
        // 读取标志
        int flag = inputStream.read();
        // 110 & 100 = 100

        // 是否是引用对象
        param.setQuote((flag & (1 << 2)) > 0);
        // 是否是异常
        param.setException((flag & (1 << 1)) > 0);
        // 是否有字段名
        boolean hasFiled = (flag & 1) > 0;
        // 读取服务
        if (param.isQuote()) {
            param.setQuoteService(readString());
        }
        // 读取字段
        if (hasFiled) {
            param.setFiledName(readString());
            param.setFieldId(readString());
        }
        // 数据长度
        int length = readInt();
        // 读取数据
        param.setData(readLength(length));
        // 读取子参数个数
        int childrenLength = readInt();
        // 读取子参数
        if (childrenLength > 0) {
            Param[] children = new Param[childrenLength];
            for (int i = 0; i < childrenLength; i++) {
                children[i] = readParam();
            }
            param.setChildren(children);
        }
        return param;
    }

    /**
     * 读取字符串
     * @return  字符串
     */
    @SneakyThrows
    private String readString() {
        byte[] bytes = readLength(readInt());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private int readInt() {
        byte[] bytes = readLength(4);
        return ByteVisit.bytesToInt(bytes);
    }

    /**
     * 读取指定长度的数据，直到读取完整为止
     * @param length    length
     * @return  byte
     */
    @SneakyThrows
    private byte[] readLength(int length) {
        byte[] bytes = new byte[length];
        if (length < 1) {
            return bytes;
        }
        int offset = 0;
        int l = 0;
        while ((l = inputStream.read(bytes, offset, length - offset)) + offset < length) {
            offset += l;
        }
        return bytes;
    }


}
