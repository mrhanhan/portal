package com.portal.core.context.serial;

import com.portal.core.context.ParamSerialization;
import com.portal.core.model.Param;

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
        List<AbstractParamSerialization> supportList = new ArrayList<>(paramSerializationSet);
        supportList = supportList.stream().filter(t -> t.isSupport(data)).collect(Collectors.toList());
        // 获取最优序列化项目 进行序列化
        supportList.sort(Comparator.comparingInt(AbstractParamSerialization::getDeep));
        return supportList.get(supportList.size() - 1).serial(data);
    }

    public void add(AbstractParamSerialization<?> paramSerialization) {
        paramSerializationSet.add(paramSerialization);
    }
}
