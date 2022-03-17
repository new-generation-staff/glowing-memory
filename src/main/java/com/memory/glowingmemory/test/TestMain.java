package com.memory.glowingmemory.test;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author zc
 */
public class TestMain {

    public static void main(String[] args) throws IOException {
        String string = "012345";
        if (string.length() > 10) {
            string = hide(string, 5, 5);
        }
        System.out.println(string);


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
    }


    public static String hide(String aims, Integer before, Integer after) {
        String beforeNum = aims.substring(0, before);
        String afterNum = aims.substring(aims.length() - after, aims.length());
        return beforeNum + "" + StringUtils.leftPad(afterNum, aims.length() - before, "*");
    }
}
