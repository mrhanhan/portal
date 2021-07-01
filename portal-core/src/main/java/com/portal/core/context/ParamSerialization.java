package com.portal.core.context;

import com.portal.core.Support;
import com.portal.core.model.Param;

/**
 * ParamSerialization
 * 参数序列化
 * @author Mrhan
 * @date 2021/7/1 11:38
 */
public interface ParamSerialization<T> extends Support<T> {
    /**
     * 序列化
     * @param data 序列化
     * @return     返回序列化后的Param
     */
    Param serial(T data);

}
