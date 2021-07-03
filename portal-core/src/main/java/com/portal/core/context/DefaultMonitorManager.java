package com.portal.core.context;

import com.portal.core.utils.NameThreadFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * DefaultMonitorManager
 *
 * @author Mrhan
 * @date 2021/7/1 15:34
 */
public class DefaultMonitorManager implements MonitorManager{

    private final Set<Monitor> monitorSet;
    private final ExecutorService executorService;

    public DefaultMonitorManager() {
        monitorSet = new HashSet<>();
        BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();
        ThreadFactory factory = new NameThreadFactory("DefaultMonitorManager");
        executorService = new ThreadPoolExecutor(10, 10000, 1, TimeUnit.MINUTES, workQueue, factory);
    }

    @Override
    public void addMonitor(Monitor monitor, boolean autoRun) {
        monitorSet.add(monitor);
        monitor.setMonitorManager(this);
        if (autoRun && monitor.getStatus() != Monitor.RUNNING_STATUS) {
            executorService.submit(() -> {
                try {
                    monitor.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void removeMonitor(Monitor monitor) {
        monitorSet.remove(monitor);
        monitor.setMonitorManager(null);
    }

    @Override
    public void onInitialize(PortalContext context) {

    }

    @Override
    public void onStartup(PortalContext context) {
    }

    @Override
    public void onShutDown(PortalContext context) {
        for (Monitor monitor : monitorSet) {
            try {
                monitor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdownNow();
    }
}
