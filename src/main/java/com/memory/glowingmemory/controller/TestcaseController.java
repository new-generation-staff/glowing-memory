package com.memory.glowingmemory.controller;

import com.memory.glowingmemory.utils.common.Result;
import com.memory.glowingmemory.utils.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zc
 */
@RestController
@RequestMapping("testcase")
@Slf4j
public class TestcaseController {

    @Value("${server.port}")
    String port;

    @GetMapping("/getPort")
    public String getPort() {
        log.info("port:{}", port);
        return port;
    }

    @PostMapping("/postCase")
    public Map postCase(@RequestBody Map map) {
        log.info("postCase : {}", map);
        return map;
    }

    @PostMapping("/errorCase")
    public Map errorCase(@RequestBody Map map) {
        log.info("errorCase : {}", map);
        int i = 1 / 0;
        return map;
    }

    @PostMapping("/resultCase")
    public Result resultCase(@RequestBody Map map) {
        log.info("resultCase : {}", map);
        return Result.result(ResultCode.SUCCESS, map);
    }
}
