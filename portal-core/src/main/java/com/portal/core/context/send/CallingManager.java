package com.portal.core.context.send;

import com.portal.core.model.Data;

import java.util.function.Consumer;

/**
 * CallingManager
 * 调用中管理器
 * 管理所有回调请求
 * @author Mrhan
 * @date 2021/6/17 16:49
 */
public interface CallingManager {
    /**
     * 调用中回调等待
     * @param service       服务
     * @param serviceId     服务ID
     * @param id            ID
     * @param callback      回调方法
     */
    void push(String service, String serviceId, String id, Consumer<Data> callback);

    /**
     * 响应并且移除回调
     * @param service   服务
     * @param serviceId 服务ID
     * @param id        ID
     * @param param     响应结果
     */
    void reply(String service, String serviceId, String id, Data param);
}
