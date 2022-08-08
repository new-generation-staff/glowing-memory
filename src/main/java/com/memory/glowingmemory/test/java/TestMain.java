package com.memory.glowingmemory.test.java;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zc
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) throws IOException {
        //测试输出debug 日志
        if (log.isDebugEnabled()) {
            log.debug("test debug");
        }

        //region list 快速添加元素
       /* list<string> list = new arraylist<>();
        collections.addall(list, "测试1", "测试2", "测试3", "测试4");
        list.add("测试5");
        system.out.println(list);*/
        //endregion

        /*List<Integer> list1 = Stream.of(1, 2, 3, 10, 11, 12).collect(Collectors.toList());
        List<Integer> list2 = Stream.of(4, 5, 6, 23, 22, 53).collect(Collectors.toList());
        List<Integer> list3 = Stream.of(7, 8, 9, 123, 45, 63).collect(Collectors.toList());
        List<Integer> integers = CollUtil.sortPageAll(1, 10, Integer::compareTo, list1, list2, list3);
        System.out.println(integers);

        List<Integer> list4 = Stream.of(32, 2, 3, 10, 11, 12).collect(Collectors.toList());
        Collections.sort(list4, Integer::compareTo);
        System.out.println(list4);


        for (int i = 0; i < 1000; i++) {
            int nextInt = new Random().nextInt(10);
            if (nextInt >= 10) {
                System.out.println("失败");
            }
        }*/


        //region 代码中访问网站url
        /*URL url = new URL("https://www.baidu.com");
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = new byte[1024];
        while (inputStream.read(bytes) >= 0) {
            System.out.println(new String(bytes));
        }*/
        //endregion
    }

    static class Solution {
        public static void main(String[] args) {
//            testA(97458);
            for (int i = 0; i < 2; i++) {
                unionLotto();
            }
        }


        public static void unionLotto() {
            int[] ints1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
            int[] ints2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
            Random random = new Random();
            Set<Integer> set = new TreeSet<>();
            while (true) {
                int ran = random.nextInt(32);
                set.add(ran);
                if (set.size() > 5) {
                    break;
                }
            }
            Iterator<Integer> it = set.iterator();
            while (it.hasNext()) {
                System.out.print(ints1[it.next()] + " ");
            }
            int ran = random.nextInt(15);
            System.out.println(", 蓝球:" + ints2[ran]);
        }

        public static void testA(int x) {
            StringBuilder value = new StringBuilder(x + "");
            System.out.println(value.length() + "位");
            System.out.println("个位数为" + x % 10);
            System.out.println("十位数为" + x / 10 % 10);
            System.out.println("百位数为" + x / 100 % 10);
            System.out.println("千位数为" + x / 1000 % 10);
            System.out.println("万位数为" + x / 10000);
            System.out.println("反转后为" + value.reverse());
        }
    }
}
