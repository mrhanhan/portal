package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;

import java.util.HashSet;
import java.util.Set;

/**
 * MultipleParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 0:11
 */
public class MultipleParamSerialization implements ParamSerialization<Object> {

    private final Set<AbstractParamSerialization<?>> paramSerializationSet;

    public MultipleParamSerialization() {
        paramSerializationSet = new HashSet<>();
    }

    @Override
    public boolean isSupport(Object o) {
        for (AbstractParamSerialization paramSerialization : paramSerializationSet) {
            if (paramSerialization.isSupport(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Param serial(Object data) {
        int deep = -1;
        AbstractParamSerialization theBest = null;
        for (AbstractParamSerialization abstractParamSerialization : paramSerializationSet) {
            if (abstractParamSerialization.isSupport(data)) {
                if (deep <= abstractParamSerialization.getDeep()) {
                    theBest = abstractParamSerialization;
                    deep = abstractParamSerialization.getDeep();
                }
            }
        }
        // 获取最优序列化项目 进行序列化
        assert theBest != null;
        return theBest.serial(data);
    }

    public void add(AbstractParamSerialization<?> paramSerialization) {
        paramSerializationSet.add(paramSerialization);
    }
}
