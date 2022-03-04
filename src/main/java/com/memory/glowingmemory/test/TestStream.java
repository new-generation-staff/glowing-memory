package com.memory.glowingmemory.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zc
 * 测试lambda相关的写法
 */
@Slf4j
public class TestStream {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun");
        List<String> newList = list.stream()
                .filter(it -> it.startsWith("J"))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        System.out.println(newList);
    }
}
