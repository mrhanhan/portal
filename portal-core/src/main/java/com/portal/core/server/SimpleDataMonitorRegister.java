package com.portal.core.server;

import com.portal.core.server.monitor.DataMonitor;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * SimpleDataMonitorRegister
 *
 * @author Mrhan
 * @date 2021/6/17 17:19
 */
@RequiredArgsConstructor
public class SimpleDataMonitorRegister implements DataMonitorRegister{

    private final Set<DataMonitor> monitorSet = new HashSet<>();

    private final ExecutorService executorService;
    @Override
    public void registerDataMonitor(DataMonitor dataMonitor) {
        monitorSet.add(dataMonitor);
        executorService.submit(dataMonitor);
    }

    @Override
    public void removeDataMonitor(DataMonitor dataMonitor) {
        monitorSet.remove(dataMonitor);
    }

    @Override
    public void close() throws Exception {
        for (DataMonitor dataMonitor : monitorSet) {
            dataMonitor.close();
        }
    }
}
