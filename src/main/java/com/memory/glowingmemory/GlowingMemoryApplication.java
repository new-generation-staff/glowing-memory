package com.memory.glowingmemory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zc
 */
@SpringBootApplication
@MapperScan("com.memory.glowingmemory.mapper")
@Configuration
@EnableAsync
public class GlowingMemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlowingMemoryApplication.class, args);
    }

}
