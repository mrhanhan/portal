package com.portal.core.server;

import com.portal.core.connect.Connection;
import com.portal.core.protocol.param.Param;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * AbstractData
 *
 * @author Mrhan
 * @date 2021/6/17 11:56
 */
@Data
@Accessors(chain = true)
public abstract class AbstractData<T extends com.portal.core.server.Data<?>> implements com.portal.core.server.Data<T> {

    private String id;

    private String service;

    private String serviceId;

    private Connection connection;

    private Param result;
    /**
     * 参数数组
     */
    private Param[] paramArray;

    public AbstractData() {
        id = UUID.randomUUID().toString();
    }
}
