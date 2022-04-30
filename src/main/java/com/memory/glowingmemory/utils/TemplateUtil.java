package com.memory.glowingmemory.utils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zc
 */
@Slf4j
public class TemplateUtil {
    // freemarker 只能转换 map中值为 String, number, date or boolean 的类型
    public static String render(String templateContent, Map<String, Object> model) {
        if (model == null) {
            return "";
        }
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("t", templateContent);
        cfg.setTemplateLoader(stringLoader);
        cfg.setDefaultEncoding("UTF-8");
        Template template;
        try {
            template = cfg.getTemplate("t");
        } catch (Exception e) {
            log.warn("Failed to load freemarker template: {}", e.getMessage());
            return "";
        }

        Writer writer = new StringWriter();
        try {
            template.process(model, writer);
        } catch (Exception e) {
            log.warn("Failed to render freemarker template: model={}, templateContent={}", model, templateContent, e);

            return "";
        }

        return writer.toString();
    }

    public static void main(String[] args) {
        String templateContent = "您好${name}，现在在测试FreeMarker功能,(插入变量1:${name@1!\"你说呢1\"}),(插入变量2:${name@2!\"你说呢2\"})";
        Map modal = new HashMap();
        modal.put("name", "[我是名字]");
        modal.put("name@1", "[变量哦]");
        modal.put("name@2", null);

        String template = render(templateContent, modal);
        if (StringUtils.isNotEmpty(template))
            log.info("template is {}", template);
    }

}
