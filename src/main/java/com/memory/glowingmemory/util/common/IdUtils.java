package com.memory.glowingmemory.util.common;

import java.util.UUID;

/**
 * @author zc
 */
public final class IdUtils {

    private IdUtils() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static String currentTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }
}
