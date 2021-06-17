package com.portal.core.protocol;

import com.portal.core.protocol.param.Param;
import com.portal.core.server.AbstractData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * JsonData
 *
 * @author Mrhan
 * @date 2021/6/17 9:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JsonData extends AbstractData<JsonData> {

    private Protocol<JsonData>  protocol;

    @Override
    public JsonData result(Param param) {
        JsonData jsonData = new JsonData();
        jsonData.setId(getId());
        jsonData.setService(getService());
        jsonData.setServiceId(getServiceId());
        jsonData.setResult(param);
        return jsonData;
    }
}
