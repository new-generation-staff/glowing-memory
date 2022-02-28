package com.memory.glowingmemory.controller;

import com.memory.glowingmemory.interfaces.AvoidRepeatableCommit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zc
 */
@RestController
@RequestMapping("/submit")
@Slf4j
public class SubmitController {


    @PostMapping("/unrepeatable")
    @AvoidRepeatableCommit
//    @AvoidRepeatableCommit(timeout = 10000)
    public String unrepeatable(@RequestBody Map map) {
        log.info("unrepeatable : {}", map);
        return "提交成功";
    }

    @RequestMapping("/repetition")
    public String repetition(@RequestBody Map map) {
        log.info("repetition : {}", map);
        return "成功了哦";
    }
}
