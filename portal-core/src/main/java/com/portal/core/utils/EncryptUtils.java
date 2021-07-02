package com.portal.core.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Md5Utils
 * Md5工具类
 * @author Mrhan
 * @date 2021/4/5 22:46
 */
public class EncryptUtils {


    /**
     * Md5加密
     * @param txt   加密内容
     * @return      返回加密后的MD5
     */
    @SneakyThrows
    public static String md5(String txt) {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = txt.getBytes(StandardCharsets.UTF_8);
        bytes = md5.digest(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte t : bytes) {
            sb.append(Integer.toHexString(t & 0xFF));
        }
        return sb.toString();
    }

    /**
     * Sha256
     * @param txt  加密内容
     * @return     返回加密后的Sha256
     */
    @SneakyThrows
    public static String sha256(String txt) {
        return encrypt(MessageDigest.getInstance("SHA-256"), txt);
    }
    /**
     * Sha-1
     * @param txt  加密内容
     * @return     返回加密后的Sha-1
     */
    @SneakyThrows
    public static String sha1(String txt) {
        return encrypt(MessageDigest.getInstance("SHA-1"), txt);
    }

    /**
     * 加密内容
     * @param md    MD5
     * @param txt   TXT
     * @return      返回内容
     */
    private static String encrypt(MessageDigest md, String txt) {
        return new String(md.digest(txt.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }
}
