package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * ArrayObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 11:04
 */
@AllArgsConstructor
public class ArrayObjectSerialization extends AbstractObjectSerialization<Object> {


    @Getter
    @Setter
    private ObjectSerialization<Object> childrenObjectSerialization;

    @Override
    public Object serial(Param param, Type type) {
        Class<?> cls = (Class<?>) type;
        Object array = null;
        Param[] children = param.getChildren();
        if (Objects.nonNull(children)) {
            array = Array.newInstance(cls.getComponentType(), children.length);
            for (int i = 0; i < children.length; i++) {
                Array.set(array, i, childrenObjectSerialization.serial(children[i], cls.getComponentType()));
            }
        }
        return array;
    }

    @Override
    public boolean isSupport(Param param, Type cls) {
        if (cls instanceof Class<?>) {
            if (((Class<?>) cls).isArray()) {
                return  param.getType() == ParamTypeEnum.ARRAY && !param.isQuote();
            }
        } return false;
    }
}
