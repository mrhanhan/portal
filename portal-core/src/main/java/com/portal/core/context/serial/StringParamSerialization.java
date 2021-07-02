package com.portal.core.context.serial;

import com.portal.core.model.Param;
import com.portal.core.model.ParamTypeEnum;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * DefaultParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/1 23:51
 */
public class StringParamSerialization extends AbstractParamSerialization<String> {

    @Override
    public Param serial(String data) {
        Param param = createParam(ParamTypeEnum.STRING);
        param.setData(Objects.isNull(data) ? new byte[0] : data.getBytes(StandardCharsets.UTF_8));
        return param;
    }

}
