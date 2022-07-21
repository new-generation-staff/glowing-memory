package com.memory.glowingmemory.test.invoke;

/**
 * @author zc
 */
public class TargetObject {
    private String key;
    private String value;

    public TargetObject() {
        value = "JavaGuide";
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    private void privateMethod() {
        System.out.println("value is " + value);
    }
}
