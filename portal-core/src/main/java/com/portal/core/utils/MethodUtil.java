package com.portal.core.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MethodUtil
 *
 * @author Mrhan
 * @date 2021/6/18 9:56
 */
@UtilityClass
public class MethodUtil {

    /**
     * 获取所有的Method
     * @param cls   Method
     * @return  Method
     */
    public List<Method> getAllMethod(Class<?> cls) {
        if (cls == Object.class) {
            return Collections.emptyList();
        }
        Method[] declaredMethods = cls.getDeclaredMethods();
        List<Method> result = new ArrayList<>(Arrays.asList(declaredMethods));
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces != null) {
            for (Class<?> anInterface : interfaces) {
                result.addAll(getAllMethod(anInterface));
            }
        }
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null) {
            result.addAll(getAllMethod(superclass));
        }
        return result;
    }

}
