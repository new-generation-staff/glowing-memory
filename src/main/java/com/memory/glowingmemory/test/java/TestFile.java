package com.memory.glowingmemory.test.java;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 */
public class TestFile {
    @Data
    public static class ImportContent {
        private String identityType = "c_cdmid";

        @ExcelProperty("CDM ID")
        private String identityValue;

        @ExcelProperty("businessId")
        private String c_retail_id;

        @ExcelProperty("dealerName")
        private String c_dealer_name;

        @ExcelProperty("dealerCode")
        private String c_placeid;

        @ExcelProperty("carLastVisit")
        private String c_car_last_visit;

        @ExcelProperty("brand")
        private String c_brand;

        @ExcelProperty("series")
        private String c_series;

        @ExcelProperty(value = "modelName", index = 7)
        private String c_model_name;
    }

    public static class DemoTestListener extends AnalysisEventListener<ImportContent> {
        /**
         * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 5;
        List<ImportContent> list = new ArrayList<>();

        @Override
        public void invoke(ImportContent importContent, AnalysisContext analysisContext) {
            if (importContent.c_car_last_visit.length() > 10) {
                importContent.c_car_last_visit = importContent.c_car_last_visit.substring(0, 10) + "T00:00:00.000Z";
            } else {
                importContent.c_car_last_visit += "T00:00:00.000Z";
            }
            list.add(importContent);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }
    }

    public static void main(String[] args) {
//        readCSV("/Users/zc/github/glowing-memory/src/main/resources/20220721数据.csv");
        readXlsx("/Users/zc/github/glowing-memory/src/main/resources/20220721.xlsx");
    }

    public static void readXlsx(String filePath) {
        List<ImportContent> list = EasyExcel.read(filePath, ImportContent.class, new DemoTestListener()).sheet().doReadSync();
        System.out.println(JSON.toJSONString(list));
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
                map.put("identityType", "c_cdmid");
                map.put("identityValue", columns[0]);
                map.put("c_retail_id", columns[1]);
                map.put("c_dealer_name", columns[2]);
                map.put("c_placeid", columns[3]);
                if (columns[4].length() > 10) {
                    map.put("c_car_last_visit", columns[4].substring(0, 10) + "T00:00:00.000Z");
                } else {
                    map.put("c_car_last_visit", columns[4] + "T00:00:00.000Z");
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
