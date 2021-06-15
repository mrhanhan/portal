package com.portal.core.model;

/**
 * IParam
 * 参数数据
 * @author Mrhan
 * @date 2021/6/9 16:32
 */
public interface IParam {

    /**
     * 获取Cls
     * @param cls   Cls
     * @return      返回Cls
     */
    <T> T getValue(Class<T> cls);

    /**
     * 是否支持指定类型数据
     * @param cls   数据
     * @return      返回指定类数据
     */
    boolean isSupport(Class<?> cls);
    /**
     * 获取Int
     * @return 获取Int
     */
    default int getInt() {
        return getValue(int.class);
    }

}
