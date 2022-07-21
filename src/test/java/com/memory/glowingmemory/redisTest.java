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

    @Test
    public void MessageDigest() {
        int a = 1;
        int b = ++a;
        System.out.println("b:" + b);
        System.out.println("a1:" + a);

        int c = a++;
        System.out.println("c:" + c);
        System.out.println("a2:" + a);

        int d = a++ + ++a;
        System.out.println("d:" + d);
        System.out.println("a3:" + a);
        Integer.valueOf(null == null ? 0 : 1);


        String string = "1234";
        System.out.println(string.substring(0, string.length()));
    }
}
