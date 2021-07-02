package com.portal.core.context.serial;

import com.portal.core.context.ObjectSerialization;
import com.portal.core.model.Param;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<AbstractObjectSerialization> objectSerializationList = new ArrayList<>(objectSerializationSet);
        objectSerializationList = objectSerializationList.stream().filter(t -> t.isSupport(param, cls)).collect(Collectors.toList());
        // 类型
        objectSerializationList.sort(Comparator.comparingInt(AbstractObjectSerialization::getDeep));
        return objectSerializationList.get(objectSerializationList.size() - 1).serial(param, cls);
    }
}
