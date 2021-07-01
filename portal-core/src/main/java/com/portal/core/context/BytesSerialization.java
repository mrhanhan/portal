package com.portal.core.context;

import com.portal.core.model.Data;

/**
 * DataSerialization
 * 数据序列化
 * @author Mrhan
 * @date 2021/7/1 17:03
 */
public interface BytesSerialization {
    /**
     * Data对象 转换为Bytes
     * @param data data
     * @return  返回Data对象
     */
    byte[] serial(Data data);
}
