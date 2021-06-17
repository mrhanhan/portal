package com.portal.core.server;

/**
 * ProtocolDataHandlerRegister
 * 数据检查项注册程序
 * @author Mrhan
 * @date 2021/6/16 9:12
 */
public interface ProtocolDataHandlerRegister {
    /**
     * 数据检查项处理程序
     * @param dataMonitor   数据检查项处理程序
     */
    void registerProtocolDataHandler(ProtocolDataHandler<? extends Data> dataMonitor);

    /**
     * 移除注册
     * @param dataMonitor   移除注册
     */
    void removeProtocolDataHandler(ProtocolDataHandler<? extends Data> dataMonitor);


}
