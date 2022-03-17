package com.memory.glowingmemory.test;

/**
 * @author zc
 */
public class User {

    private int id;
    private String name;
    private String sex;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void getUser() {
        System.out.println(id + ":" + name + ":" + sex);
    }
}
