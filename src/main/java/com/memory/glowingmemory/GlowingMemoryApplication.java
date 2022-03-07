package com.memory.glowingmemory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author zc
 */
@SpringBootApplication
@MapperScan("com.memory.glowingmemory.mapper")
@Configuration
public class GlowingMemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlowingMemoryApplication.class, args);
    }

}
