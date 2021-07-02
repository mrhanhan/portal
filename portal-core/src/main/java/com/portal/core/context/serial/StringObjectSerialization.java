package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * StringObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 9:21
 */
public class StringObjectSerialization extends AbstractObjectSerialization<String> {
    @Override
    public String serial(Param param, Type cls) {
        byte[] data = param.getData();
        if (data == null) {
            return "";
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public boolean isSupport(Param param, Type cls) {
        return param.getType() == ParamTypeEnum.STRING && cls instanceof Class && ((Class<?>)cls).isAssignableFrom(String.class);
    }
}
