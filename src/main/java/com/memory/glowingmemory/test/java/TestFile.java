package com.memory.glowingmemory.test.java;


import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author zc
 */
public class TestFile {
    public static void main(String[] args) {
        readCSV("/Users/zc/github/glowing-memory/src/main/resources/20220720数据.csv");
    }

    public static void readCSV(String filePath) {
        List<Map> list = new ArrayList<>();
        // 创建 reader  全路径
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            // CSV文件的分隔符
            String DELIMITER = ",";
            // 按行读取
            String line;
            while ((line = br.readLine()) != null) {
                Map<Object, Object> map = new HashMap<>();
                // 分割
                String[] columns = line.split(DELIMITER);
                map.put("date", "2022-07-20T10:30:00.000Z");
                map.put("identityType", "c_cdmid");
                map.put("identityValue", columns[0]);
                map.put("c_retail_id", columns[1]);
                map.put("c_dealer_name", columns[2]);
                map.put("c_placeid", columns[3]);
                if (columns[4].length()>10){
                    map.put("c_car_last_visit", columns[4].substring(0,10)+"T00:00:00.000Z");
                }else {
                    map.put("c_car_last_visit", columns[4]+"T00:00:00.000Z");
                }
                map.put("c_brand", columns[5]);
                map.put("c_series", columns[6]);
                map.put("c_model_name", columns[7]);
                list.add(map);
            }
            System.out.println(JSON.toJSONString(list));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
}
