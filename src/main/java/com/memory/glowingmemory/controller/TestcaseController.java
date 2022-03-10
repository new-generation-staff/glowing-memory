package com.memory.glowingmemory.controller;

import com.memory.glowingmemory.services.TestCaseService;
import com.memory.glowingmemory.utils.common.Result;
import com.memory.glowingmemory.utils.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TestCaseService testCaseService;

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

    //region 测试@Async注解
    @PostMapping("/asynchronization")
    public void asynchronization(@RequestBody Map map) {
        log.info("asynchronization : {}", map);
        log.info("主方法启动------");
        testCaseService.async1();
        testCaseService.async2();
        log.info("主方法结束------");
    }
    //endregion

    //region wait与notify、notifyAll
    private Object object = new Object();

    @GetMapping("/produce/{id}")
    public Result produce(@PathVariable("id") String id) throws InterruptedException {
        synchronized (object) {
            log.info("{} 进入", id);
            object.wait();
            log.info("{} 被唤醒", id);
        }
        return Result.result(ResultCode.SUCCESS);
    }

    /*
     * 书籍认为notify是随机唤醒一个，notifyall是全部唤醒，但顺序也是随机，因为对象锁是非公平锁
     * 但具体怎么实现取决于jvm，Java 1.8 使用的jvm HotSpot下，用的队列，notify是有序的，先进先出
     * 详见  NotifyTest 类
     * */
    @GetMapping("/notify")
    public Result cmd_notify() {
        synchronized (object) {
            object.notify();
        }
        return Result.result(ResultCode.SUCCESS);
    }

    @GetMapping("/notifyAll")
    public Result cmd_notifyAll() {
        synchronized (object) {
            object.notifyAll();
        }
        return Result.result(ResultCode.SUCCESS);
    }
    //endregion


}
