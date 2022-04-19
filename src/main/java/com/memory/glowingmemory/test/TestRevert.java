package com.memory.glowingmemory.test;

/**
 * @author zc
 */
public class TestRevert {
    // revert commit 为撤销提交, 回滚到选中的提交 上次提交后的样子(即鼠标在【第13次提交】点击revert commit时，回滚到【第12次提交】时，此时可以rollback)
    public static void main(String[] args) {
        System.out.println("第一次提交01");
        System.out.println("第一次提交02");
        System.out.println("第三次提交01");
        System.out.println("第11次提交01");
        System.out.println("第12次提交01");
    }
}
