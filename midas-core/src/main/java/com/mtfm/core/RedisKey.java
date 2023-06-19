package com.mtfm.core;

import java.util.Arrays;

public abstract class RedisKey {

    private static final String KEY_SEPARATOR = ":";
    public static final String SCAN_ALL = "*";

    public static String makeKey(String key, String...patterns) {
        StringBuffer buffer = new StringBuffer(key);
        Arrays.stream(patterns).sorted().forEach(item -> buffer.append(KEY_SEPARATOR).append(item));
        return buffer.toString();
    }

}
