package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;

import java.util.Objects;

/**
 * DefaultParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
public class NullParamSerialization extends AbstractParamSerialization<Object> {

    @Override
    public Param serial(Object data) {
        return createParam(ParamTypeEnum.NULL);
    }

    @Override
    public boolean isSupport(Object o) {
        return Objects.isNull(o);
    }

    @Override
    public int getDeep() {
        return 1;
    }
}
