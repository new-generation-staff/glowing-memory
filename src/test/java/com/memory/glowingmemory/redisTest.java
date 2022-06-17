package com.memory.glowingmemory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;

public class redisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void redisCase() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "23");
        map.put("sex", "男");
        map.put("birthday", LocalDateTime.now());
        redisTemplate.opsForHash().putAll("user", map);
        //使用entries获取整个user对象
        System.out.println("user：" + redisTemplate.opsForHash().entries("user"));
    }
}