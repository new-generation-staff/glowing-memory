package com.memory.glowingmemory.test;

/**
 * @author zc
 */
public class User {

    private int id;
    private String name;
    private String sex;

    public User() {
        System.out.println("第一步 执行无参构造方法");
    }

    public void setId(int id) {
        this.id = id;
        System.out.println("第二步 set方法 Id");

    }

    public void setName(String name) {
        this.name = name;
        System.out.println("第二步 set方法 Name");
    }

    public void setSex(String sex) {
        this.sex = sex;
        System.out.println("第二步 set方法 Sex");
    }

    public void getUser() {
        System.out.println(id + ":" + name + ":" + sex);
    }

    public void initMethod() {
        System.out.println("第三步 初始化");
    }

    public void destroyMethod() {
        System.out.println("第五步 销毁");
    }
}
