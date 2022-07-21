package com.memory.glowingmemory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zc
 */
@SpringBootApplication
@MapperScan("com.memory.glowingmemory.mapper")
@EnableAsync  //注解开启异步任务的执行
@EnableScheduling //开启定时任务
public class GlowingMemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlowingMemoryApplication.class, args);
    }

}
