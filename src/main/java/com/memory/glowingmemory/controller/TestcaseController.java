package com.memory.glowingmemory.controller;

import com.alibaba.fastjson.JSON;
import com.memory.glowingmemory.services.InitService;
import com.memory.glowingmemory.util.common.*;
import com.memory.glowingmemory.pojo.PostRequest;
import com.memory.glowingmemory.services.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

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
    private RedisTemplate redisTemplate;

    @Autowired
    InitService initService;

    @Autowired
    KafkaTemplate kafkaTemplate;

    /**
     * @autowired写在变量上的注入要等到类完全加载完，才会将相应的bean注入, 而变量是在加载类的时候按照相应顺序加载的，所以变量的加载要早于@autowired变量的加载，
     * 那么给变量prefix 赋值的时候所使用的a，其实还没有被注入，所以报空指针，而使用构造器就在加载类的时候将a加载了，这样在内部使用a给prefix 赋值就完全没有问题。
     * <p>
     * Java变量的初始化顺序为：
     * 静态变量或静态语句块–>实例变量或初始化语句块–>构造方法中的变量–>@Autowired下的变量
     */
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


    @PostMapping("/redisCase")
    public void redisCase() throws InterruptedException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "23");
        map.put("sex", "男");
        map.put("birthday", TimeUtils.cstNow());
        redisTemplate.opsForHash().putAll("user", map);
        //使用entries获取整个user对象
        System.out.println(redisTemplate.opsForHash().get("user", "birthday"));
        System.out.println(redisTemplate.opsForHash().get("user", "birthday") instanceof LocalDateTime);
        System.out.println(((LocalDateTime) redisTemplate.opsForHash().get("user", "birthday")).plusHours(-8));
    }

    @PostMapping("/threadCase")
    public void threadCase() {
        ThreadPoolExecutor executor = initService.getExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("mock service");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    log.error("threadCase Exception", e);
                }
            }
        });
    }

    @Value("${kafka.testTopic.topic}")
    String testTopic;

    @PostMapping("/kafkaCase")
    public void kafkaCase() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        String data = JSON.toJSONString(map);


        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(testTopic, IdUtils.uuid(), data);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Send event to topic {} success. request={}", testTopic, map);
            }

            @Override
            public void onFailure(Throwable e) {
                log.warn("Send event to topic {} failed. request={}", testTopic, map, e);
            }
        });
    }
}
