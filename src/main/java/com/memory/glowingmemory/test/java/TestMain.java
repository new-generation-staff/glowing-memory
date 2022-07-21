package com.memory.glowingmemory.test.java;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author zc
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) throws IOException {
        HashMap<String, String> request = new HashMap<>();
        request.put("customerId", "1");
        request.put("identityType", "2");
        request.put("identityValue", "3");
        HashMap<String, String> clone = (HashMap<String, String>) request.clone();
        clone.put("aaa", "4");
        clone.remove("customerId");
        Map<Object, Object> objectObjectMap = Collections.emptyMap();

        Optional<Map> empty = Optional.of(new HashMap());
        Map map = empty.get();
        map.put("bbb", "111");

        System.out.println(request);
        System.out.println(clone);
        System.out.println(map);

        //list 快速添加元素
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "测试1", "测试2", "测试3", "测试4");
        list.add("测试5");
        System.out.println(list);
        /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println(time);

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleFormatter.format(new Date());
        System.out.println(format);*/



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

//        writeFile();

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
