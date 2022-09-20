package com.memory.glowingmemory.test.java;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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
@Slf4j
public class TestFile {
    @Data
    public static class ImportContent {
        private String identityType = "c_cdmid";

        //@ExcelProperty("CDM ID")
        @ExcelProperty(index = 0)
        private String identityValue;

        //@ExcelProperty("businessId")
        @ExcelProperty(index = 1)
        private String c_retail_id;

        //@ExcelProperty("dealerName")
        @ExcelProperty(index = 2)
        private String c_dealer_name;

        //@ExcelProperty("dealerCode")
        @ExcelProperty(index = 3)
        private String c_placeid;

        //@ExcelProperty("carLastVisit")
        @ExcelProperty(index = 4)
        private String c_car_last_visit;

        //@ExcelProperty("brand")
        @ExcelProperty(index = 5)
        private String c_brand;

        // @ExcelProperty("series")
        @ExcelProperty(index = 6)
        private String c_series;

        @ExcelProperty(value = "modelName", index = 7)
        private String c_model_name;
    }

    public static class DemoTestListener extends AnalysisEventListener<ImportContent> {
        /**
         * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 99;
        List<ImportContent> list = ListUtils.newArrayListWithExpectedSize(100);

        @Override
        public void invoke(ImportContent importContent, AnalysisContext analysisContext) {
            if (importContent.c_car_last_visit.length() > 10) {
                importContent.c_car_last_visit = importContent.c_car_last_visit.substring(0, 10) + "T00:00:00.000Z";
            } else {
                importContent.c_car_last_visit += "T00:00:00.000Z";
            }
            list.add(importContent);

            if (list.size() >= BATCH_COUNT) {
                manageList(list);
                // 存储完成清理 list
                list.clear();
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            manageList(list);
            list.clear();
        }
    }

    static RestTemplate restTemplate = new RestTemplate(getFactory());

    public static void main(String[] args) {
        readXlsx("/Users/zc/github/glowing-memory/src/main/resources/MOTOR_SAL_20220725-0804.xlsx");
    }

    public static void readXlsx(String filePath) {
        EasyExcel.read(filePath, ImportContent.class, new DemoTestListener()).sheet().doRead();
    }

    public static void manageList(List<ImportContent> list) {
        log.info("list:{}", JSON.toJSONString(list));
//        testHttp(list);
    }

    public static void testHttp(List<ImportContent> list) {
        String token = "f1357991fa2a16cc35f3a1d9b5d2af3c---";
        String url = String.format("url?access_token=%s", token);

        HashMap map = new HashMap<>();
        map.put("event", "c_resend");

        HashMap body = new HashMap<>();
        body.put("commonProps", map);
        body.put("events", list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> httpEntity = new HttpEntity<>(body, headers);


        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);

        log.info("responseEntity.getStatusCode:{},getBody:{}", responseEntity.getStatusCode(), responseEntity.getBody());
    }

    public static void readCSV(String filePath) {
        List<Map> list = new ArrayList<>();
        // CSV文件的分隔符
        String DELIMITER = ",";
        // 创建 reader  全路径
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
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
            log.info("list:{}", JSON.toJSONString(list));
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

    public static HttpComponentsClientHttpRequestFactory getFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(60000);
        factory.setReadTimeout(60000);
        return factory;

    }
}
