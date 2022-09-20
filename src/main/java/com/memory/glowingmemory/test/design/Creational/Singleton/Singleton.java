package com.memory.glowingmemory.test.design.Creational.Singleton;

/**
 * @author zc
 * <p>
 * 写单例模式的注意要点
 * 1、构造方法私有化
 * 2、提供一个静态方法返回
 * 3、静态变量对象，可以懒汉式，也可以饿汉式。
 * 那么对于doublecheck的方式，还需要注意
 * 1、需要有2个if。第一个，提示效率，第二个防止第一次的时候，多个线程都在这里等着，比如第二个线程进去之后第二个if就可以判断第一个线程是否真正的创建成功了对象。
 * 2、instance = new SingletonController();可能会指令重排序，比如先赋值地址，再在内中开辟内存空间存对象。所以可能第一个if只是有了地址，那它就不会null，就直接返回了，这个时候就用问题。因此，必须instance要使用volatile保证有序性，防止指令重排序。
 * <p>
 * 另外，单例是可以破坏的，比如万恶之源的反射，序列化，clone等，不过都可以使用响应的解决办法予以预防，具体见代码
 */
public class Singleton {

    //默认是第一次创建
    private static volatile boolean isCreate = false;

    //本类内部创建对象实例
    private static volatile Singleton instance;

    //获取单例的方法
    public static Singleton getSingleton() {
        if (null == instance) {
            synchronized (Singleton.class) {
                if (null == instance) {
                    instance = new Singleton();//若不加volatile，会产生指令重排序。原因是，可能先赋值地址值给instance，然后再创建对象
                }
            }
        }
        return instance;
    }

    /**
     * 构造方法私有化，外部不能new，防止反射
     */
    private Singleton() {
        if (isCreate) {
            throw new RuntimeException("已然被实例化一次，不能在实例化");
        }
        isCreate = true;
        System.out.println("Hello World Singleton");
    }

    /**
     * 防止克隆
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return instance;
    }

    /**
     * 防止序列化破环
     *
     * @return
     */
    private Object readResolve() {
        return instance;
    }
}
