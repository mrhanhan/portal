package com.portal.core.server;

import com.portal.core.server.monitor.DataMonitor;

/**
 * DefaultProtocolDataHandlerRegister
 *
 * @author Mrhan
 * @date 2021/6/16 9:24
 */
public interface DataMonitorRegister extends AutoCloseable{
    /**
     * 数据检查项处理程序
     * @param dataMonitor   数据检查项处理程序
     */
    void registerDataMonitor(DataMonitor dataMonitor);

    /**
     * 移除注册
     * @param dataMonitor   移除注册
     */
    void removeDataMonitor(DataMonitor dataMonitor);


}
