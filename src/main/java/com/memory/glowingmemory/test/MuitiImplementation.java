package com.memory.glowingmemory.test;


import lombok.extern.slf4j.Slf4j;

abstract class D {
    D() {
        System.out.println("初始化抽象类D");
    }

}

abstract class E {
    E() {
        System.out.println("初始化抽象类E");
    }

    abstract void a();
}

class Z extends D {
    E makeE() {
        return new E() {
            @Override
            void a() {
            }
        };
    }
}


/**
 * @author zc
 * 使用内部类达成 一个类继承两个抽象类的目的
 */
@Slf4j
public class MuitiImplementation {
    static void tasksD(D d) {
    }


    static void tasksE(E e) {
    }


    public static void main(String[] args) {
        System.out.println("启动main方法");
        Z z = new Z();
        tasksD(z);
        tasksE(z.makeE());
    }
}
