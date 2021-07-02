package com.portal.core.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ClassUtil
 *
 * @author Mrhan
 * @date 2021/7/2 0:03
 */
@UtilityClass
public class ClassUtil {


    /**
     * 获取类型级别
     * @param cls   类型
     * @return      返回类型的级别
     */
    public int getDeep(Class<?> cls) {
        int i = 0;
        while (cls != null && cls != Object.class) {
            i ++;
            cls = cls.getSuperclass();
        }
        return i;
    }

    public boolean isAssignable(Class<?> targetClass, Class<?> ...classes) {
        if (classes != null) {
            for (Class<?> aClass : classes) {
                if (targetClass.isAssignableFrom(aClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取所有字段信息
     * @return  获取所有字段信息
     */
    public List<Field> getAllFiled(Class<?> cls) {
        Field[] declaredFields = cls.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>(Arrays.asList(declaredFields));
        // 判断上级
        if (cls.getSuperclass() != null  && cls.getSuperclass() != Object.class) {
            fieldList.addAll(getAllFiled(cls.getSuperclass()));
        }
        return fieldList;
    }
}
