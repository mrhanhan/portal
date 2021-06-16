package com.portal.core.protocol;

import com.portal.core.connect.Connection;

/**
 * SimpleTextData
 *
 * @author Mrhan
 * @date 2021/6/16 13:43
 */
@lombok.Data
public class SimpleTextData implements Data{

    private String service;

    private Protocol<SimpleTextData>  protocol;

    private Connection connection;

    @Override
    public Protocol<SimpleTextData> getProtocol() {
        return protocol;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
