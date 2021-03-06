package com.portal.core.connect;

import com.portal.core.context.send.CallingManager;
import lombok.Getter;
import lombok.Setter;

/**
 * AbstractConnect
 *
 * @author Mrhan
 * @date 2021/6/15 16:41
 */
public abstract class AbstractConnection implements Connection {

    @Getter
    @Setter
    private ConnectionManager manager;
    @Getter
    @Setter
    private ConnectMetadata metadata;

    @Getter
    @Setter
    private ConnectionSession session;

    @Getter
    @Setter
    private CallingManager callingManager;

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void close() throws Exception {
        if (manager instanceof AbstractCacheConnectionManager) {
            ((AbstractCacheConnectionManager) manager).removeConnection(this);
        }
        // 清除内容
        session.getServiceContainer().clear();
    }
}
