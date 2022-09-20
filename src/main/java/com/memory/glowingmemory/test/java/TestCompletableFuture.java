package com.memory.glowingmemory.test.java;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author zc
 */
@Slf4j
public class TestCompletableFuture {
    public static void main(String[] args) {
        log.info("testFuture = {}", testFuture());
    }

    /**
     * CompletableFuture使用详解
     * https://blog.csdn.net/sermonlizhi/article/details/123356877
     *
     * @return
     */
    public static String testFuture() {
        //合并任务，有返回值
        CompletableFuture<String> completable1 = CompletableFuture.supplyAsync(() -> {
            log.info("aa,thread:{}", Thread.currentThread().getName());
            return "aa";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info("bb,thread:{}", Thread.currentThread().getName());
            return "bb";
        }), (a, b) -> {
            log.info("a + b,thread:{}", Thread.currentThread().getName());
            return a + b;
        });

        //用来连接两个有依赖关系的任务，结果由第二个任务返回
        CompletableFuture<String> completable2 = CompletableFuture.supplyAsync(() -> {
            log.info("cc,thread:{}", Thread.currentThread().getName());
            return "cc";
        }).thenCompose(e -> CompletableFuture.supplyAsync(() -> {
            log.info("dd,thread:{}", Thread.currentThread().getName());
            return e + "dd";
        }));

        //前面任务的执行结果，交给后面的Function
        CompletableFuture<String> completable3 = CompletableFuture.supplyAsync(() -> {
            log.info("ee,thread:{}", Thread.currentThread().getName());
            return "ee";
        }).thenApply(e -> e + "ff");
        log.info("ff,thread:{}", Thread.currentThread().getName());


        CompletableFuture<Void> completable4 = CompletableFuture.supplyAsync(() -> {
            log.info("gg,thread:{}", Thread.currentThread().getName());
            return "gg";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            log.info("hh,thread:{}", Thread.currentThread().getName());
            return "hh";
        }), (a, b) -> {
            log.info("gg+hh,thread:{}", Thread.currentThread().getName());
            log.info(a + b);
        });

        return completable3.join();
    }
}
