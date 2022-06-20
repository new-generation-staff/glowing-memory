package com.memory.glowingmemory.util.common;

/**
 * @author zc
 */

public enum ResultCode {
    SUCCESS(200, "success"),
    FAILURE(401, "failure"),
    ERROR(400, "error"),
    LOGIN_OVERRUN(4001, "失败次数过多"),
    REGISTER_FAILURE(4002, "用户名已存在"),
    LOGIN_FAILURE(4003, "用户名或密码错误");

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
