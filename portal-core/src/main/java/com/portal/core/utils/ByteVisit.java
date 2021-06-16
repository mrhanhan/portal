package com.portal.core.utils;

import lombok.experimental.UtilityClass;


/**
 * ByteVisit
 *
 * @author Mrhan
 * @date 2021/6/16 13:45
 */
@UtilityClass
public class ByteVisit {


    /**
     * 开始位置
     * @param bytes 对于数据
     * @param data  开始位置
     * @return  开始位置对比
     */
    public boolean equ(byte[] bytes, byte ... data) {
        int start = 0;
        for (byte d : data) {
            if (bytes[start] != d) {
                return false;
            }
        }

        return true;
    }
}
