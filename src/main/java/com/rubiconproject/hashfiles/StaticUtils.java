package com.rubiconproject.hashfiles;

public interface StaticUtils {

    public static void validateNotNull(Object obj, String msg) {
        assert obj != null : msg;
    }

}
