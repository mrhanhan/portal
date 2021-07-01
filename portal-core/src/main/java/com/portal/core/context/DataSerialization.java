package com.portal.core.context;

import com.portal.core.model.Data;

/**
 * DataSerialization
 * 数据序列化
 * @author Mrhan
 * @date 2021/7/1 17:03
 */
public interface DataSerialization {
    /**
     * Bytes 转换为Data对象
     * @param bytes bytes
     * @return  返回Data对象
     */
    Data serial(byte[] bytes);
}
