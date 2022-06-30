package com.memory.glowingmemory.test.java;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zc
 */
public class NotifyTest {

    private final Object object = new Object();
    private List<Integer> sleepList = new LinkedList<>();
    private List<Integer> notifyList = new LinkedList<>();

    public void startThread(int i) {
        new Thread(new Runnable() { // 启动一个线程
            @Override
            public void run() {
                synchronized (object) {
                    try {
                        sleepList.add(i);
                        object.wait();
                        notifyList.add(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        NotifyTest notifyTest = new NotifyTest();
        for(int i = 1; i < 30; i++){
            notifyTest.startThread(i);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 1; i < 30; i++){
            Thread.sleep(10);
            synchronized (notifyTest.object) {
                notifyTest.object.notify();
            }
        }
        System.out.println("休眠顺序" + notifyTest.sleepList);
        System.out.println("唤醒顺序" + notifyTest.notifyList);
    }
}

