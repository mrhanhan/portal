package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import com.portal.core.utils.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ObjectObjectSerialization
 * 对象序列化
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
@AllArgsConstructor
public class ObjectObjectSerialization extends AbstractObjectSerialization<Object> {
    @Getter @Setter
    private ObjectSerialization<Object> childrenObjectSerialization;

    @SneakyThrows
    @Override
    public Object serial(Param param, Type type) {
        Class<?> cls = (Class<?>) type;
        Object o = cls.newInstance();
        Param[] children = param.getChildren();
        if (Objects.nonNull(children)) {
            Map<String, Param> fieldMap = new HashMap<>();
            for (Param child : children) {
                fieldMap.put(child.getFiledName(), child);
            }
            List<Field> fieldList = ClassUtil.getAllFiled(cls);
            for (Field field : fieldList) {
                String name = ObjectParamSerialization.generatorFiledName(field);
                if (fieldMap.containsKey(name)) {
                    Param childParam =  fieldMap.get(name);
                    field.setAccessible(true);
                    field.set(o, childrenObjectSerialization.serial(childParam, field.getGenericType()));
                }
            }
        }
        return o;
    }

    @Override
    public boolean isSupport(Param param, Type type) {
        if (param.getType() != ParamTypeEnum.OBJECT) {
            return false;
        }
        if (type instanceof Class) {
            Class<?> cls = (Class<?>) type;
            Constructor<?>[] constructors = cls.getConstructors();
            for (Constructor<?> c : constructors) {
                if (c.getParameterCount() == 0) {
                    return !param.isQuote();
                }
            }
        }
        return false;
    }

    @Override
    public int getDeep() {
        return 0;
    }
}
