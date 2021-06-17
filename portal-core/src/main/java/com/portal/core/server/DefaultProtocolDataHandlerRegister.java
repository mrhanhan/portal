package com.portal.core.server;

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

    private final Set<ProtocolDataHandler<Data<?>>> protocolDataHandlerSet;

    public DefaultProtocolDataHandlerRegister() {
        protocolDataHandlerSet = new HashSet<>();
    }

    @Override
    @SuppressWarnings("all")
    public void registerProtocolDataHandler(ProtocolDataHandler protocolDataHandler) {
        protocolDataHandlerSet.add(protocolDataHandler);
    }

    @Override
    public void removeProtocolDataHandler(ProtocolDataHandler<?> protocolDataHandler) {
        protocolDataHandlerSet.remove(protocolDataHandler);
    }

    /**
     * 获取支持处理此数据的协议数据处理程序
     * @param data  协议数据处理程序
     * @return      协议数据处理程序
     */
    public List<ProtocolDataHandler<Data<?>>> getSupportList(byte[] data) {
        List<ProtocolDataHandler<Data<?>>> protocolDataHandlerList = new ArrayList<>(protocolDataHandlerSet);
        return protocolDataHandlerList.stream().filter(t -> t.isSupport(data)).collect(Collectors.toList());
    }

    /**
     * 获取支持处理此数据的协议数据处理程序
     * @param data  协议数据处理程序
     * @return      协议数据处理程序
     */
    public List<ProtocolDataHandler<Data<?>>> getSupportList(Data<?> data) {
        List<ProtocolDataHandler<Data<?>>> protocolDataHandlerList = new ArrayList<>(protocolDataHandlerSet);
        return protocolDataHandlerList.stream().filter(t -> t.isSupport(data)).collect(Collectors.toList());
    }

}
