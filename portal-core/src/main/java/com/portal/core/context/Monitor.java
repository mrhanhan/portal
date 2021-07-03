package com.portal.core.context;

/**
 * Monitor
 *
 * @author Mrhan
 * @date 2021/6/15 20:24
 */
public interface Monitor extends Runnable, AutoCloseable{
    /**
     * 运行中
     */
    int RUNNING_STATUS = 1;
    /**
     * 已停止
     */
    int STOP_STATUS = 1;

    /**
     * 获取检测箱状态
     * @return  状态
     */
    int getStatus();

    /**
     * 设置检查项管理器
     * @param manager   检查项管理器
     */
    void setMonitorManager(MonitorManager manager);

    /**
     * 获取检查项管理器
     * @return  检查项管理器
     */
    MonitorManager getMonitorManager();
}
