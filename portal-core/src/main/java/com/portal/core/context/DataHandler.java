package com.portal.core.context;

import com.portal.core.context.monitor.DataMonitor;

/**
 * DataHandler
 *
 * @author Mrhan
 * @date 2021/7/1 16:45
 */
public interface DataHandler {
    /**
     * 数据Handler
     * @param monitor   DataMonitor
     * @param bytes      bytes
     */
    void onHandler(DataMonitor monitor, byte[] bytes);
}
