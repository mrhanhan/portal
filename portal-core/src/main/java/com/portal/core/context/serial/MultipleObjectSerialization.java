package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.model.Param;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * MultipleParamSerialization
 *
 * @author Mrhan
 * @date 2021/7/2 0:11
 */
public class MultipleObjectSerialization implements ObjectSerialization<Object> {

    private final Set<AbstractObjectSerialization<?>> objectSerializationSet;

    public MultipleObjectSerialization() {
        objectSerializationSet = new HashSet<>();
    }


    public void add(AbstractObjectSerialization<?> abstractObjectSerialization) {
        objectSerializationSet.add(abstractObjectSerialization);
    }

    @Override
    public boolean isSupport(Param param, Type cls) {
        for (AbstractObjectSerialization abstractObjectSerialization : objectSerializationSet) {
            if (abstractObjectSerialization.isSupport(param, cls)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object serial(Param param, Type cls) {

        int deep = -1;
        AbstractObjectSerialization theBest = null;
        for (AbstractObjectSerialization abstractObjectSerialization : objectSerializationSet) {
            if (abstractObjectSerialization.isSupport(param, cls)) {
                if (deep <= abstractObjectSerialization.getDeep()) {
                    theBest = abstractObjectSerialization;
                    deep = abstractObjectSerialization.getDeep();
                }
            }
        }
        // 获取最优序列化项目 进行序列化
        if (theBest == null) {
            System.err.println("无法找到Object序列化:" + param + " \n\tClass:" + cls);
        }
        assert theBest != null;
        return theBest.serial(param, cls);
    }
}
