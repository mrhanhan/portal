package com.portal.core.protocol;

import com.portal.core.protocol.param.Param;
import com.portal.core.server.AbstractData;
import lombok.EqualsAndHashCode;

/**
 * SimpleTextData
 *
 * @author Mrhan
 * @date 2021/6/16 13:43
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class SimpleTextData extends AbstractData<SimpleTextData> {
    private Protocol<SimpleTextData>  protocol;

    @Override
    public Protocol<SimpleTextData> getProtocol() {
        return protocol;
    }

    @Override
    public SimpleTextData result(Param param) {
        SimpleTextData result = new SimpleTextData();
        result.setService(getService());
        result.setServiceId(getServiceId());
        result.setResult(param);
        return result;
    }

}
