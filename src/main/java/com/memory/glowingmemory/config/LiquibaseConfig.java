package com.memory.glowingmemory.config;

//import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;


/**
 * @author zc
 */
//Configuration 代表为配置类，替代xml配置文件
@Configuration
public class LiquibaseConfig {
    /*@Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        //指定changelog的位置，这里使用的一个master文件引用其他文件的方式
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        //liquibase.setContexts("development,test,production");
        liquibase.setShouldRun(true);
        return liquibase;
    }*/
}
