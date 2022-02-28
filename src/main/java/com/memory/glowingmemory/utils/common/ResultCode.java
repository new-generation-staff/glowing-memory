package com.memory.glowingmemory.utils.common;

/**
 * @author zc
 */

public enum ResultCode {
    SUCCESS(200, "success"),
    FAILURE(401, "failure"),
    ERROR(400, "error");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
