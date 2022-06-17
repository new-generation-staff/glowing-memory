package com.memory.glowingmemory.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zc
 */
@Slf4j
@Service
public class InitService {

    //线程池中核心线程数的最大值
    @Value("${thread.corePoolSize}")
    int corePoolSize;

    //线程池中能拥有最多线程数
    @Value("${thread.maximumPoolSize}")
    int maximumPoolSize;

    //keepAliveTime: 表示空闲线程的存活时间
    //TimeUnit unit: 表示keepAliveTime的单位
    @Value("${thread.keepAliveTime}")
    int keepAliveTime;

    @Value("${thread.arrayBlock}")
    int arrayBlock;

    public ThreadPoolExecutor executor;

    //@PostContruct是Java自带的注解，在方法上加该注解会在spring容器初始化的时候执行该方法。
    @PostConstruct
    public void init() {
        log.info("项目启动 init:{}");
        //创建线程池
        //https://blog.csdn.net/liuxiao723846/article/details/108026782
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue(arrayBlock));
    }

    public ThreadPoolExecutor getExecutor() {
        if (executor == null) {
            log.info("ThreadPoolExecutor is null");
            executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue(arrayBlock));
        }
        return executor;
    }
}
