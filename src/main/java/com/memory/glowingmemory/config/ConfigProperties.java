package com.memory.glowingmemory.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zc
 */
@Component
//读取yml配置文件下的内容，自动填充到属性中
@ConfigurationProperties(prefix = "server")
@Getter
@Setter
public class ConfigProperties {
    private String port;
}
