package com.memory.glowingmemory.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zc
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) throws IOException {
        //region 打码
        /*String string = "012345";
        if (string.length() > 10) {
            string = hide(string, 5, 5);
        }
        System.out.println(string);*/
        //endregion

        //region switch处理Long类型
        /*Long a = 1L;
        switch (a.toString()) {
            case "1":
                System.out.println(11);
                break;
            case "2":
                System.out.println(22);
                break;
        }*/
        //endregion

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

        writeFile();

    }

    public static void writeFile() {
        File file = new File("segment.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            for (int i = 0; i < 2000000; i++) {
                fileWriter.write("WBAZCEHZBPCH" + i + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String hide(String aims, Integer before, Integer after) {
        String beforeNum = aims.substring(0, before);
        String afterNum = aims.substring(aims.length() - after, aims.length());
        return beforeNum + "" + StringUtils.leftPad(afterNum, aims.length() - before, "*");
    }

    static class Solution {
        public static void main(String[] args) {
//            testA(97458);
            for (int i = 0; i < 2; i++) {
                testB();
            }
        }

        public static void testB() {
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
