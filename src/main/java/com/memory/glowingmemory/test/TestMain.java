package com.memory.glowingmemory.test;

import java.io.IOException;

/**
 * @author zc
 */
public class TestMain {

    public static void main(String[] args) throws IOException {
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
}
