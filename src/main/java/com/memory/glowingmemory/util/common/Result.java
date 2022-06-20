package com.memory.glowingmemory.util.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zc
 */
@Data
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public static Result result(ResultCode resultCode, Object... data) {
        Result result = new Result();
        result.code = resultCode.code();
        result.message = resultCode.message();
        result.data = data;
        return result;
    }
}
