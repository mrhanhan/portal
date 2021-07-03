package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;

import java.lang.reflect.Type;

/**
 * StringObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 9:21
 */
public class NullObjectSerialization extends AbstractObjectSerialization<Object> {
    @Override
    public Object serial(Param param, Type cls) {
        return null;
    }

    @Override
    public boolean isSupport(Param param, Type cls) {
        return param.getType() == ParamTypeEnum.NULL;
    }

    @Override
    public int getDeep() {
        return 1;
    }
}
