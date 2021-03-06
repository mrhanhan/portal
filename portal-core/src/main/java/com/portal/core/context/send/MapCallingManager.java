package com.portal.core.context.send;

import com.portal.core.model.Data;
import com.portal.core.model.Param;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * MapCallingManager
 *
 * @author Mrhan
 * @date 2021/6/17 16:55
 */
public class MapCallingManager implements CallingManager {

    private final Map<String, Consumer<Data>> MAP = new HashMap<>();

    @Override
    public synchronized void push(String service, String serviceId, String id, Consumer<Data> callback) {
        String key = generateKey(service, serviceId, id);
        MAP.put(key, callback);
    }


    @Override
    public synchronized void reply(String service, String serviceId, String id, Data param) {
        String key = generateKey(service, serviceId, id);
        if (MAP.containsKey(key)) {
            MAP.get(key).accept(param);
            MAP.remove(key);
        }
    }

    /**
     * 生成key
     * @param service   服务
     * @param serviceId 服务ID
     * @param id        ID
     * @return          返回唯一KEY
     */
    private String generateKey(String service, String serviceId, String id) {
        return String.format("%s:%s:%s", service, serviceId, id);
    }
}
