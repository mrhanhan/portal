package com.portal.core.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * AbstractProtocol
 *
 * @author Mrhan
 * @date 2021/6/16 13:42
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractProtocol<T extends Data> implements Protocol<T> {


    private final String name;
    private final String version;

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public boolean isCompatibleOld() {
        return false;
    }
}
