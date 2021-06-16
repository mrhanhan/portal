package com.portal.core.server;

import com.portal.core.server.monitor.DataMonitor;

/**
 * DataHandler
 * 数据处理程序
 * @author Mrhan
 * @date 2021/6/15 20:25
 */
public interface DataHandler {
    /**
     * 处理数据
     * @param monitor 检查项
     * @param data  处理数据
     * @return 返回处理后的数据
     */
    void onHandler(DataMonitor monitor, byte[] data);

}
