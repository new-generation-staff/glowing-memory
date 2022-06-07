package com.memory.glowingmemory.controller;

import com.memory.glowingmemory.utils.common.RequestAttributes;
import com.memory.glowingmemory.pojo.PostRequest;
import com.memory.glowingmemory.services.TestCaseService;
import com.memory.glowingmemory.utils.common.Result;
import com.memory.glowingmemory.utils.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 测试case
 *
 * @author zc
 */
@RestController
@RequestMapping("testcase")
@Slf4j
public class TestcaseController {

    @Value("${server.port}")
    String port;

    @Autowired
    @Qualifier(value = "testCaseServiceImpl")
    //使用 Autowired 根据类型注入 配合使用 Qualifier 根据名称注入
    //@Resource 可以根据类型和名称注入 默认根据类型注入； @Resource（name = "testCaseServiceImpl"）根据名称注入
    private TestCaseService testCaseService;

    @PostMapping("/noteCase")
    // @Valid注解表明需要验证这个对象的属性
    public String noteCase(@Valid @RequestBody PostRequest request,
                           @RequestAttribute(RequestAttributes.REQUEST_ID) String requestId,
                           @RequestAttribute(RequestAttributes.TENANT_ID) String tenantId) {
        log.info("requestId={},tenantId={}, request={}", requestId, tenantId, request);

        return port;
    }


    @PostMapping("/cloneCase")
    public String cloneCase(@Valid @RequestBody PostRequest request,
                            @RequestAttribute(RequestAttributes.REQUEST_ID) String requestId,
                            @RequestAttribute(RequestAttributes.TENANT_ID) String tenantId) {
        log.info("requestId={},tenantId={}, request={}", requestId, tenantId, request);

        try {
            PostRequest clone = request.clone();
            clone.setTenantId(3333);
            clone.setRequestId("11111");
            clone.setCampaignUuid("uuid");
            log.info("request={}", request);
            log.info("clone={}", clone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return port;
    }


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

    @PostMapping("/errorCase2")
    public Map errorCase2(@RequestBody Map map) {
        try {
            log.info("errorCase2 : {}", map);
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("error: {} \n {}", e.getMessage(), e.getStackTrace());
        }
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


    @PostMapping("/sleepCase")
    public Map sleepCase(@RequestBody Map map) throws InterruptedException {
        Thread.sleep(10000);
        return map;
    }

}
