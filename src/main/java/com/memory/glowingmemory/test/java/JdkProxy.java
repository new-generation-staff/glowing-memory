package com.memory.glowingmemory.test.java;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author zc
 * 使用jdk的动态代理,只能代理实现了接口的类或者直接代理接口
 */
public class JdkProxy {

    public static void main(String[] args) {
//        TestSpringAopInterface instance = (TestSpringAopInterface) Proxy.newProxyInstance(TestSpringAopInterfaceImpl.class.getClassLoader(), interfaces, new TestSpringAopInterfaceProxy(new TestSpringAopInterfaceImpl()));
        TestSpringAopInterface instance = (TestSpringAopInterface) JdkProxyFactory.getProxy(new TestSpringAopInterfaceImpl());
        System.out.println(instance.add(1, 3));
        System.out.println(instance.update("2"));
    }
}

class JdkProxyFactory {
    /**
     * loader :类加载器，用于加载代理对象。
     * interfaces : 被代理类实现的一些接口；
     * h : 实现了 InvocationHandler 接口的对象
     * Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
     */
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),         // 目标类的类加载
                target.getClass().getInterfaces(),          // 代理需要实现的接口，可指定多个
                new TestSpringAopInterfaceProxy(target)     // 代理对象对应的自定义 InvocationHandler
        );
    }
}

//创建代理对象
class TestSpringAopInterfaceProxy implements InvocationHandler {
    private Object object;
    public TestSpringAopInterfaceProxy(Object object) {
        this.object = object;
    }
    /**
     * @param proxy  动态生成的代理类
     * @param method 与代理类对象调用的方法相对应
     * @param args   当前 method 方法的参数
     * @return
     * @throws Throwable
     */
    //增强的逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("args:" + Arrays.toString(args));

        //增强方法的执行
        Object invoke = method.invoke(object, args);

        return invoke;
    }
}
