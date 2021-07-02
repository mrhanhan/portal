package com.portal.core.utils;

import lombok.SneakyThrows;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * ByteCache
 *
 * @author Mrhan
 * @date 2021/6/16 14:03
 */
public class ByteCache  extends ByteArrayOutputStream {

    public ByteCache() {
    }

    public ByteCache(int size) {
        super(size);
    }

    @SneakyThrows
    public void writeString(String string) {
        super.write(string.getBytes(StandardCharsets.UTF_8));
    }
}
