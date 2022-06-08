package com.memory.glowingmemory.intercepors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zc
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoggerInterceptor loggerInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/**")
                .addResourceLocations("classpath:/static/");
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/redirect/login")
                .excludePathPatterns("/redirect/index")
                .excludePathPatterns("/redirect/signup")
                .excludePathPatterns("/login/*")
                /*.excludePathPatterns("/loginHtml/index.html")
                .excludePathPatterns("/loginHtml/login.html")
                .excludePathPatterns("/loginHtml/signup.html")*/
                .excludePathPatterns("/testcase/*")
                .excludePathPatterns("/static/**");
    }
}
