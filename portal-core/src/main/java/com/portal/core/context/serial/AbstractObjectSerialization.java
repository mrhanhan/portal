package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.model.Param;
import com.portal.core.utils.ClassUtil;
import lombok.Getter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * AbstractParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 23:52
 */
public abstract class AbstractObjectSerialization<T> implements ObjectSerialization<T> {

    /**
     * 泛型类型
     */
    private ParameterizedType parameterizedType;
    @Getter
    private Class<?> genericType;

    public AbstractObjectSerialization() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) genericSuperclass;
            genericType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
    }
    /**
     * 获取类型深度
     * @return  返回深度
     */
    public int getDeep() {
        return ClassUtil.getDeep(genericType);
    }

    @Override
    public T serial(Param param, SerializationOptions options) {
        return serial(param, options.getSerialType());
    }

    public T serial(Param param, Type type) {
        return null;
    }

    @Override
    public boolean isSupport(Param param, SerializationOptions options) {
        return isSupport(param, options.getSerialType());
    }

    public boolean isSupport(Param param, Type type) {
        return false;
    }
}
