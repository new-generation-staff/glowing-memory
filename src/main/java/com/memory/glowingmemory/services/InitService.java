package com.memory.glowingmemory.services;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * @author zc
 */
@Slf4j
public class InitService {
    /*
        @PostContruct是Java自带的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
    */
    @PostConstruct
    private void init() {
        log.info("项目启动 init");
    }
}
