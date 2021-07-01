package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;

/**
 * DefaultParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
public class DefaultParamSerialization implements ParamSerialization<Object> {
    @Override
    public boolean isSupport(Object o) {
        return true;
    }

    @Override
    public Param serial(Object data) {

        return null;
    }
}
