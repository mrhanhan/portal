package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * CollectionParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 11:04
 */
@AllArgsConstructor
public class CollectionParamSerialization extends AbstractParamSerialization<Collection> {

    @Getter
    @Setter
    private ParamSerialization<Object> childrenParamSerialization;

    @Override
    public Param serial(Collection data, SerializationOptions options) {
        Param param = createParam(ParamTypeEnum.ARRAY);
        int size = data.size();
        Param[] children = new Param[size];
        int i = 0;
        for (Object obj : data) {
            children[i ++] = childrenParamSerialization.serial(obj, options.copy().parseType(obj));
        }
        param.setChildren(children);
        return param;
    }
}
