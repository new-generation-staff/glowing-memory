package com.memory.glowingmemory.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author zc
 * 使用jdk的动态代理
 */
public class JDKProxy {

    public static void main(String[] args) {
        Class[] interfaces = {TestSpringAopInterface.class};

        TestSpringAopInterface instance = (TestSpringAopInterface) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new TestSpringAopInterfaceProxy(new TestSpringAopInterfaceImpl()));
        System.out.println(instance.add(1, 3));
    }
}


//创建代理对象
class TestSpringAopInterfaceProxy implements InvocationHandler {

    //1、把要创建的代理对象传进来

    private Object object;

    public TestSpringAopInterfaceProxy(Object object) {
        this.object = object;
    }

    //增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("args:" + Arrays.toString(args));

        //增强方法的执行
        Object invoke = method.invoke(object, args);

        return invoke;
    }
}
