package com.portal.core.utils;

import com.portal.core.model.Data;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
    public Data readData() {
        Data data = new Data();

        return data;
    }

}
