package com.nilknow.util;

import java.nio.charset.StandardCharsets;

public class DecodeUtil {
    public static String decode(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
