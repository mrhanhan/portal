package com.portal.core.protocol;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.AbstractData;
import com.portal.core.server.Data;
import lombok.EqualsAndHashCode;

/**
 * SimpleTextData
 *
 * @author Mrhan
 * @date 2021/6/16 13:43
 */
@EqualsAndHashCode(callSuper = true)
@lombok.Data
public class SimpleTextData extends AbstractData implements Data {
    private Protocol<SimpleTextData>  protocol;

    @Override
    public Protocol<SimpleTextData> getProtocol() {
        return protocol;
    }

}
