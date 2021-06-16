package com.portal.core.server;

import com.portal.core.Support;
import com.portal.core.protocol.Data;

/**
 * ProtocolDataHandler
 *
 * @author Mrhan
 * @date 2021/6/15 20:28
 */
public interface ProtocolDataHandler extends DataHandler<byte[], Data>, Support<byte[]> {

}
