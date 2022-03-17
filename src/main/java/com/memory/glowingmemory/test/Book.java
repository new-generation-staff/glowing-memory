package com.memory.glowingmemory.test;

/**
 * @author zc
 */
public class Book {
    private int id;
    private String name;
    private String author;

    public Book(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public void getBook() {
        System.out.println(id + ":" + name + ":" + author);
    }
}
