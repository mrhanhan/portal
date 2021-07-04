package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
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
public abstract class AbstractParamSerialization<T> implements ParamSerialization<T> {

    /**
     * 泛型类型
     */
    private ParameterizedType parameterizedType;
    @Getter
    private Class<?> genericType;

    public AbstractParamSerialization() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) genericSuperclass;
            genericType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
    }

    @Override
    public boolean isSupport(T data, SerializationOptions options) {
        return isSupport(data);
    }

    public boolean isSupport(T t) {
        return t != null && genericType.isAssignableFrom(t.getClass());
    }

    @Override
    public Param serial(T data, SerializationOptions options) {
        return serial(data);
    }

    public Param serial(T data) {
        return null;
    }

    /**
     * 获取类型深度
     * @return  返回深度
     */
    public int getDeep() {
        return ClassUtil.getDeep(genericType);
    }

    /**
     * 创建默认的Param
     * @param field 字段名称
     * @param type  字段类型
     * @return  Param
     */
    protected Param createParam(String field, ParamTypeEnum type) {
        Param param = new Param();
        param.setType(type);
        param.setQuote(false);
        param.setException(false);
        param.setFiledName(field);
        return param;
    }

    /**
     * 创建默认的Param
     * @param type  字段类型
     * @return  Param
     */
    protected Param createParam(ParamTypeEnum type) {
        return createParam(null, type);
    }

}
