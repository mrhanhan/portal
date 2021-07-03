package com.portal.core.context;

import com.portal.core.context.monitor.DataMonitor;
import com.portal.core.model.Data;

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
     * @param data      data
     */
    void onHandler(DataMonitor monitor, Data data);
}
