package com.portal.core.server;

/**
 * DataHandlerContext
 * 数据处理程序注册器
 * @author Mrhan
 * @date 2021/6/16 9:15
 */
public interface DataHandlerContext {
    /**
     * 数据注册处理程序
     * @param handler   注册数据处理程序
     */
    void register(DataHandler<?, ?> handler);

    /**
     * 移除数据注册处理程序
     * @param handler   移除注册的数据处理程序
     */
    void remove(DataHandler<?, ?> handler);

}
