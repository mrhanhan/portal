package com.portal.core.context.serial;

import com.portal.core.connect.Connection;
import com.portal.core.context.send.SendData;
import com.portal.core.service.ServiceContainer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * SerializationOptions
 * 连接
 *
 * @author Mrhan
 * @date 2021/7/4 10:38
 */
@Data
@Accessors(chain = true)
public class SerializationOptions {

    private Connection connection;

    private ServiceContainer serviceContainer;

    private Type serialType;

    private SendData sendData;


    public SerializationOptions(Connection connection) {
        this.connection = connection;
    }

    public SerializationOptions parseType(Object o) {
        return setSerialType(Objects.nonNull(o) ? o.getClass() : null);
    }

    public static SerializationOptions of(Connection connection, Type type) {
        return new SerializationOptions(connection).setSerialType(type);
    }

    public SerializationOptions copy() {
        SerializationOptions options = new SerializationOptions(connection);
        options.setServiceContainer(serviceContainer);
        options.setSerialType(serialType);
        options.setSendData(sendData);
        return options;
    }
}
