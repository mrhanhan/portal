package com.portal.core.context;

import com.portal.core.model.Param;

import java.lang.reflect.Type;

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
     * @param type   Type
     * @return      返回序列化后的Param
     */
    T serial(Param param, Type type);

    /**
     * 是否依赖支持
     * @param param Param
     * @param type   Type
     * @return  返回Boolan
     */
    boolean isSupport(Param param, Type type);
}
