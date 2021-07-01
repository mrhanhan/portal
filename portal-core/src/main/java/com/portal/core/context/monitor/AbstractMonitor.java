package com.portal.core.context.monitor;

import com.portal.core.context.Monitor;

/**
 * AbstractMonitor
 *
 * @author Mrhan
 * @date 2021/7/1 15:44
 */
public abstract class AbstractMonitor implements Monitor {

    public int status;

    @Override
    public synchronized int getStatus() {
        return status;
    }

    /**
     * 开始
     */
    public synchronized void begin() {
        status = RUNNING_STATUS;
    }

    /**
     * 是否运行状态
     * @return  是否运行状态
     */
    public synchronized boolean isRunning() {
        return status == RUNNING_STATUS;
    }

    /**
     * 结束
     */
    public synchronized void end() {
        status = STOP_STATUS;
    }

    public synchronized void modify(int status) {
        this.status = status;
    }

    public synchronized boolean isStatus(int status) {
        return this.status == status;
    }

}
