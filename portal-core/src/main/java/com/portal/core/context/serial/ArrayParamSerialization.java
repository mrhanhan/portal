package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;

/**
 * ArrayParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 11:04
 */
@AllArgsConstructor
public class ArrayParamSerialization extends AbstractParamSerialization<Object> {


    @Getter
    @Setter
    private ParamSerialization<Object> childrenParamSerialization;


    @Override
    public Param serial(Object data, SerializationOptions options) {
        Param param = createParam(ParamTypeEnum.ARRAY);
        int size = Array.getLength(data);
        Param[] children = new Param[size];
        for (int i = 0; i < size; i++) {
            Object o = Array.get(data, i);
            children[i] = childrenParamSerialization.serial(o, options.copy().parseType(o));
        }
        param.setChildren(children);
        return param;
    }

    @Override
    public boolean isSupport(Object o) {
        return o != null && o.getClass().isArray();
    }

    @Override
    public int getDeep() {
        return 1;
    }
}
