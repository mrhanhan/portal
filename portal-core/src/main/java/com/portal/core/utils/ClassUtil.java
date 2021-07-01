package com.portal.core.utils;

import lombok.experimental.UtilityClass;

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
        while (cls == Object.class) {
            i ++;
            cls = cls.getSuperclass();
        }
        return i;
    }
}
