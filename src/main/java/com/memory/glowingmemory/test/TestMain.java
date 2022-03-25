package com.memory.glowingmemory.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zc
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) throws IOException {
        try {
            int i = 0;
            int a = 1 / i;
        } catch (Exception e) {
            log.error("error:{}", e.getMessage(), e.getStackTrace());
        }

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

    }


    public static String hide(String aims, Integer before, Integer after) {
        String beforeNum = aims.substring(0, before);
        String afterNum = aims.substring(aims.length() - after, aims.length());
        return beforeNum + "" + StringUtils.leftPad(afterNum, aims.length() - before, "*");
    }
}
