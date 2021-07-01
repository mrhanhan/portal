package com.portal.core.context;

/**
 * MonitorManager
 * 检测项管理器
 * @author Mrhan
 * @date 2021/7/1 13:59
 */
public interface MonitorManager extends PortalLifeCycle{
    /**
     * 添加检测项
     * @param monitor   添加检测项
     * @param autoRun   自动运行
     */
    void addMonitor(Monitor monitor, boolean autoRun);

    /**
     * 移除Monitor
     * @param monitor   移除检测项
     */
    void removeMonitor(Monitor monitor);
}
