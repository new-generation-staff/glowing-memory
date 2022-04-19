package com.memory.glowingmemory.test;

/**
 * @author zc
 */
public class TestSpringAopInterfaceImpl implements TestSpringAopInterface {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public String update(String string) {
        return string;
    }
}
