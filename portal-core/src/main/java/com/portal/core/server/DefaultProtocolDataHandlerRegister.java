package com.portal.core.server;

import com.portal.core.protocol.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DefaultProtocolDataHandlerRegister
 *
 * @author Mrhan
 * @date 2021/6/16 9:24
 */
public class DefaultProtocolDataHandlerRegister implements ProtocolDataHandlerRegister{

    private final Set<ProtocolDataHandler<? extends Data>> protocolDataHandlerSet;

    public DefaultProtocolDataHandlerRegister() {
        protocolDataHandlerSet = new HashSet<>();
    }

    @Override
    public void registerProtocolDataHandler(ProtocolDataHandler<? extends Data> dataMonitor) {
        protocolDataHandlerSet.add(dataMonitor);
    }

    @Override
    public void removeProtocolDataHandler(ProtocolDataHandler<? extends Data> dataMonitor) {
        protocolDataHandlerSet.remove(dataMonitor);
    }

    /**
     * 获取支持处理此数据的协议数据处理程序
     * @param data  协议数据处理程序
     * @return      协议数据处理程序
     */
    List<ProtocolDataHandler<? extends Data>> getSupportList(byte[] data) {
        List<ProtocolDataHandler<? extends Data>> protocolDataHandlerList = new ArrayList<>(protocolDataHandlerSet);
        return protocolDataHandlerList.stream().filter(t -> t.isSupport(data)).collect(Collectors.toList());
    }

    /**
     * 获取支持处理此数据的协议数据处理程序
     * @param data  协议数据处理程序
     * @return      协议数据处理程序
     */
    List<ProtocolDataHandler<? extends Data>> getSupportList(Data data) {
        List<ProtocolDataHandler<? extends Data>> protocolDataHandlerList = new ArrayList<>(protocolDataHandlerSet);
        return protocolDataHandlerList.stream().filter(t -> t.isSupport(data)).collect(Collectors.toList());
    }

}
