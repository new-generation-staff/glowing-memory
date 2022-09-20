package com.memory.glowingmemory.config;

import com.memory.glowingmemory.util.common.Result;
import com.memory.glowingmemory.util.common.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zc
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result errorHandler(Exception ex) {
        Map map = new HashMap();
        //判断异常的类型,返回不一样的返回值
        if (ex instanceof MissingServletRequestParameterException) {
            map.put("msg", "缺少必需参数：" + ((MissingServletRequestParameterException) ex).getParameterName());
        } else {
            map.put("msg", "这是系统异常:" + ex.getMessage());
        }
        return Result.result(ResultCode.ERROR,map);
    }
}

