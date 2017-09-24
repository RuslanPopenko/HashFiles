package com.rubiconproject.hashfiles;

/**
 * Utils interface which provides commonly uses methods
 */
public interface StaticUtils {

    /**
     * Throws AssertError with msg if object is null
     *
     * @param obj
     * @param msg
     */
    public static void validateNotNull(Object obj, String msg) {
        assert obj != null : msg;
    }

}
