package com.portal.core.protocol;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.param.Param;
import com.portal.core.server.Data;

/**
 * SimpleTextData
 *
 * @author Mrhan
 * @date 2021/6/16 13:43
 */
@lombok.Data
public class SimpleTextData implements Data {

    private String service;

    private String serviceId;

    private Protocol<SimpleTextData>  protocol;

    private Connection connection;
    /**
     * 参数数组
     */
    private Param[] paramArray;

    @Override
    public Protocol<SimpleTextData> getProtocol() {
        return protocol;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

}
