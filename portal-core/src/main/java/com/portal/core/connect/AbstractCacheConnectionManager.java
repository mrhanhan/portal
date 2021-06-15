package com.portal.core.connect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AbstractConnectionManager
 *
 * @author Mrhan
 * @date 2021/6/15 16:49
 */
public abstract class AbstractCacheConnectionManager implements ConnectionManager{

    private final Map<ConnectMetadata, IConnection> cacheMap;

    public AbstractCacheConnectionManager() {
        cacheMap = createCacheMap();
    }

    @Override
    public IConnection getConnection(ConnectMetadata metadata) throws Exception {
        IConnection iConnect = null;
        if (cacheMap.containsKey(metadata)) {
            iConnect = cacheMap.get(metadata);
            if (iConnect.isAvailable()) {
                return iConnect;
            }
        }
        if (isSupport(metadata)) {
            throw new IllegalArgumentException("not support "  + metadata.getClass().getSimpleName());
        }
        iConnect = createConnection(metadata);

        cacheMap.put(metadata, iConnect);
        return iConnect;
    }

    /**
     * 通过元数据创建IConnect
     * @param metadata      ConnectMetadata
     * @return              IConnection
     * @throws Exception    创建连接的时候的错误问题
     */
    protected abstract IConnection createConnection(ConnectMetadata metadata) throws Exception;

    /**
     * 创建用于缓存的Map
     * @return Map
     */
    protected  Map<ConnectMetadata, IConnection> createCacheMap() {
        return new HashMap<>(6);
    }

    @Override
    public void close() throws IOException {
        Map<ConnectMetadata, IConnection> connectionMap = new HashMap<>(this.cacheMap);
        this.cacheMap.clear();
        connectionMap.forEach((k, v) -> {
            if (v.isAvailable()) {
                try {
                    v.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        connectionMap.clear();
    }
}
