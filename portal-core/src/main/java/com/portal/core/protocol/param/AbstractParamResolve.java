package com.portal.core.protocol.param;

import com.portal.core.service.ServiceContainer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * DefaultParamResolve
 * 默认参数解析
 * @author Mrhan
 * @date 2021/6/17 9:52
 */
public class AbstractParamResolve implements ParamResolve{


    protected final Map<ParamTypeEnum, BiFunction<Param, Type, Object>> objectResolveMap;

    public AbstractParamResolve() {
        objectResolveMap = new HashMap<>();
    }

    @Override
    public <T> T resolve(Param param, Type cls) {
        // TODO
        Object result = null;
        if (objectResolveMap.containsKey(param.getType())) {
            result = objectResolveMap.get(param.getType()).apply(param, cls);
        }
        return (T)result;
    }

    @Override
    public Param resolve(Object obj, ServiceContainer serviceContainer) {
        // TODO
        return new Param();
    }
}
