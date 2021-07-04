package com.portal.core.context;

import com.portal.core.context.serial.SerializationOptions;
import com.portal.core.model.Param;

/**
 * ParamSerialization
 * 参数序列化
 * @author Mrhan
 * @date 2021/7/1 11:38
 */
public interface ObjectSerialization<T>  {
    /**
     * 序列化
     * @param param 序列化
     * @param options   Type
     * @return      返回序列化后的Param
     */
    T serial(Param param, SerializationOptions options);

    /**
     * 是否依赖支持
     * @param param Param
     * @param options   Type
     * @return  返回Boolean
     */
    boolean isSupport(Param param, SerializationOptions options);
}
