package com.memory.glowingmemory.test.java;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zc
 * ThreadLocal的基础用法; ！！！线程池中使用ThreadLocal会导致内存泄漏，谨慎使用（或在使用结束后调用remove方法）
 * 视频地址：https://www.bilibili.com/video/BV1Tq4y1L7NY?p=4&spm_id_from=pageDriver
 */
@Slf4j
public class ThreadLocalTest {
    ThreadLocal<String> name = new ThreadLocal<>();

    void setName(String name) {
        this.name.set(name);
    }

    String getName() {
        return this.name.get();
    }

    void removeName() {
        this.name.remove();
    }

    public static void main(String[] args) {
        ThreadLocalTest localTest = new ThreadLocalTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                localTest.setName("first name");
                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("线程1==={}", localTest.getName());
                //使用时放到finally中
                localTest.removeName();
                log.info("线程1===2{}", localTest.getName());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                localTest.setName("Second name");
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("线程2==={}", localTest.getName());
                localTest.removeName();
                log.info("线程2===2{}", localTest.getName());
            }
        }).start();

    }

}
