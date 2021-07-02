package com.portal.core.context.serial;

import com.portal.core.model.Param;

import java.nio.charset.StandardCharsets;

/**
 * StringObjectSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 9:21
 */
public class StringObjectSerialization extends AbstractObjectSerialization<String> {
    @Override
    public String serial(Param param, Class<? extends String> cls) {
        byte[] data = param.getData();
        if (data == null) {
            return "";
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    @Override
    public boolean isSupport(Param param, Class<? extends String> cls) {
        return cls.isAssignableFrom(cls);
    }
}
