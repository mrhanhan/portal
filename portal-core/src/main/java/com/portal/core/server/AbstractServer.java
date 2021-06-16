package com.portal.core.server;

import com.portal.core.Server;
import com.portal.core.connect.Connection;

/**
 * AbstractServer
 *
 * @author Mrhan
 * @date 2021/6/15 17:47
 */
public abstract class AbstractServer implements Server, ProtocolDataHandler, ConnectionHandler<Connection>, ProtocolDataHandlerRegister {

}
