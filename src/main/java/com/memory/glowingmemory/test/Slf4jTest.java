package com.memory.glowingmemory.test;


import lombok.extern.slf4j.Slf4j;

/**
 * @author zc
 */
@Slf4j
public class Slf4jTest {

    public static void main(String[] args) {
        System.out.println("打印日志");
        log.info("输出日志: {}","日志啊啊啊啊");
        System.out.println("打印日志");
    }
}
