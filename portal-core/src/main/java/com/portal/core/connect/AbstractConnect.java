package com.portal.core.connect;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractConnect
 *
 * @author Mrhan
 * @date 2021/6/15 16:41
 */
public abstract class AbstractConnect implements IConnection {

    @Getter
    @Setter
    private ConnectionManager manager;
    @Getter
    @Setter
    private ConnectMetadata metadata;


    @Override
    public boolean isAvailable() {
        return true;
    }
}
