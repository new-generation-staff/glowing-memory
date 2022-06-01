package com.memory.glowingmemory.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author zc
 */
@Slf4j
public class TestFile {
    public static void main(String[] args) {
        /**
         * 2022-05-13 00:00 -- 2022-05-13 16:00 {"c_usedcar_h5_get_successfully":75,"c_usercar_wx_landingpage":40,"c_usedcar_h5_book_successfully":75}
         * 2022-05-13 16:00 -- 2022-05-14 16:00 {"c_usedcar_h5_get_successfully":135,"c_usercar_wx_landingpage":190,"c_rss_send_successfully":1,"c_usedcar_h5_book_successfully":135}
         * 2022-05-14 16:00 -- 2022-05-15 16:00 {"c_usedcar_h5_get_successfully":95,"c_usercar_wx_landingpage":96,"c_rss_send_successfully":1,"c_usedcar_h5_book_successfully":95}
         * 2022-05-15 16:00 -- 2022-05-16 16:00 {"c_usedcar_h5_get_successfully":76,"c_usercar_wx_landingpage":35,"c_usedcar_h5_book_successfully":76}
         * 2022-05-16 16:00 -- 2022-05-17 16:00 {"c_usedcar_h5_get_successfully":84,"c_usercar_wx_landingpage":44,"c_rss_send_successfully":1,"c_usedcar_h5_book_successfully":84}
         * 2022-05-17 16:00 -- 2022-05-18 16:00 {"c_usedcar_h5_get_successfully":94,"c_usercar_wx_landingpage":35,"c_rss_send_successfully":1,"c_usedcar_h5_book_successfully":94}
         * 2022-05-18 16:00 -- 2022-05-19 16:00 {"c_usedcar_h5_get_successfully":74,"c_usercar_wx_landingpage":25,"c_rss_send_successfully":2,"c_usedcar_h5_book_successfully":74}
         */
        readFile();
//        writeFile();
    }

    public static void readFile() {
        String fileName = "newFile.txt";
        try {
            Stream<String> lines = Files.lines(Paths.get(fileName));
            List<Map> c_rss_send_successfullyList = new ArrayList<>(),
                    c_afs_h5_book_successfullyList = new ArrayList<>(),
                    c_afs_h5_get_successfullyList = new ArrayList<>(),
                    c_usedcar_h5_book_successfullyList = new ArrayList<>(),
                    c_usedcar_h5_get_successfullyList = new ArrayList<>(),
                    c_afs_wx_book_successfullyList = new ArrayList<>(),
                    c_afs_wx_get_successfullyList = new ArrayList<>(),
                    c_usedcar_wx_book_successfullyList = new ArrayList<>(),
                    c_usedcar_wx_get_successfullyList = new ArrayList<>(),
                    c_recall_book_successfullyList = new ArrayList<>(),
                    c_recall_cancel_successfullyList = new ArrayList<>(),
                    c_usercar_wx_landingpageList = new ArrayList<>(),
                    c_usercar_sms_landingpageList = new ArrayList<>();

            SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            HashMap<Object, Integer> hashMap = new HashMap<>();

            lines.forEach(ele -> {
                JSONObject message = JSONObject.parseObject(ele);
                Map<String, Object> map = new HashMap<>();
                String time = simpleFormatter.format(new Date());

                /**
                 * 统计各个活动的人数
                 */
                if (message.get("eventid") != null) {
                    if (hashMap.get(message.get("eventid")) != null) {
                        hashMap.put(message.get("eventid"), hashMap.get(message.get("eventid")) + 1);
                    } else {
                        hashMap.put(message.get("eventid"), 1);
                    }
                }

                /**
                 * 构建事件
                 */
                JSONObject context = (JSONObject) message.get("context");

                String c_send_time = "";
                try {
                    Date parse = simpleFormatter.parse((String) context.get("c_send_time"));
                    Calendar cl = Calendar.getInstance();
                    cl.setTime(parse);
                    cl.add(Calendar.HOUR, -8);
                    c_send_time = simpleFormatter.format(cl.getTime());

                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                map.put("identityType", "c_copid");
                map.put("identityValue", context.get("c_copid"));
                map.put("date", time);
                map.put("c_send_time", c_send_time);

                if ("c_usedcar_h5_get_successfully".equals(message.get("eventid"))) {
                    map.put("c_brand", context.get("c_brand"));
                    map.put("c_url", context.get("c_url"));
                    map.put("c_coupon_name", context.get("c_coupon_name"));
                    c_usedcar_h5_get_successfullyList.add(map);
                } else if ("c_usedcar_h5_book_successfully".equals(message.get("eventid"))) {
                    map.put("c_brand", context.get("c_brand"));
                    map.put("c_dealer_name", context.get("c_dealer_name"));
                    map.put("c_dealer_add", context.get("c_dealer_add"));
                    map.put("c_dealer_phone", context.get("c_dealer_phone"));
                    map.put("c_dealer_id", context.get("c_dealer_id"));
                    c_usedcar_h5_book_successfullyList.add(map);
                } else if ("c_usercar_wx_landingpage".equals(message.get("eventid"))) {
                    map.put("identityType", "wechat");
                    map.put("identityValue", message.get("userid"));
                    c_usercar_wx_landingpageList.add(map);
                }
            });
            System.out.println("hashMap" + JSONObject.toJSONString(hashMap));


            HashMap<Object, Object> c_usedcar_h5_get_successfullyMap = new HashMap<>(),
                    c_usedcar_h5_book_successfullyMap = new HashMap<>(),
                    c_usercar_wx_landingpageMap = new HashMap<>();
            HashMap<Object, Object> c_usedcar_h5_get_successfullyEvent = new HashMap<>(),
                    c_usedcar_h5_book_successfullyEvent = new HashMap<>(),
                    c_usercar_wx_landingpageEvent = new HashMap<>();
            c_usedcar_h5_get_successfullyEvent.put("event", "c_usedcar_h5_get_successfully");
            c_usedcar_h5_get_successfullyMap.put("events", c_usedcar_h5_get_successfullyList);
            c_usedcar_h5_get_successfullyMap.put("commonProps", c_usedcar_h5_get_successfullyEvent);
            log.info("c_usedcar_h5_get_successfullyMap:{}", JSONObject.toJSONString(c_usedcar_h5_get_successfullyMap));


            c_usedcar_h5_book_successfullyEvent.put("event", "c_usedcar_h5_book_successfully");
            c_usedcar_h5_book_successfullyMap.put("events", c_usedcar_h5_book_successfullyList);
            c_usedcar_h5_book_successfullyMap.put("commonProps", c_usedcar_h5_book_successfullyEvent);
            log.info("c_usedcar_h5_book_successfullyList:{}", JSONObject.toJSONString(c_usedcar_h5_book_successfullyMap));


            c_usercar_wx_landingpageEvent.put("event", "c_usercar_wx_landingpage");
            c_usercar_wx_landingpageMap.put("events", c_usercar_wx_landingpageList);
            c_usercar_wx_landingpageMap.put("commonProps", c_usercar_wx_landingpageEvent);
            log.info("c_usercar_wx_landingpageList:{}", JSONObject.toJSONString(c_usercar_wx_landingpageMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile() {
        File file = new File("segment.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            for (int i = 0; i < 20; i++) {
                fileWriter.write("WBAZCEHZBPCH" + i + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
