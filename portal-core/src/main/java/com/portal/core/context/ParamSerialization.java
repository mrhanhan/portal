package com.portal.core.context;

import com.portal.core.context.serial.SerializationOptions;
import com.portal.core.model.Param;

/**
 * ParamSerialization
 * 参数序列化
 * @author Mrhan
 * @date 2021/7/1 11:38
 */
public interface ParamSerialization<T> {
    /**
     * 序列化
     * @param data 序列化
     * @param options   序列化参数
     * @return     返回序列化后的Param
     */
    Param serial(T data, SerializationOptions options);
    /**
     * 是否依赖支持
     * @param data Param
     * @param options   Type
     * @return  返回Boolean
     */
    boolean isSupport(T data, SerializationOptions options);
}
