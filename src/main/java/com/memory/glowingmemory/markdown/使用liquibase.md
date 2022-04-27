# 不使用liquibase
# 1、注释pom文件中的liquibase依赖
# 2、注释LiquibaseConfig类




```shell
liquibase通过日志文件的形式记录数据库的变更，然后执行日志文件中的修改，将数据库更新或回滚到一致的状态。它的目标是提供一种数据库类型无关的解决方案，通过执行schema类型的文件来达到迁移
可以通过xml反向 生成表、修改表
```

# 步骤0 导入liquibase的依赖

```markdown

<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

# 步骤1.1 application.yml 文件中添加配置，指定去找那个配置文件

```markdown
# Liquibase 配置

liquibase:
    url: ${spring.datasource.url}
    driver-class-name: ${spring.datasource.driver-class-name}
    user: ${spring.datasource.username}
    password: ${spring.datasource.password}
    change-log: classpath:liquibase/master.xml
```

# 步骤1.2 或者添加LiquibaseConfig类（已在yaml中配置数据库的连接信息）

```shell
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        //指定changelog的位置，这里使用的一个master文件引用其他文件的方式
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setShouldRun(true);
        return liquibase;
    }
}
```

# 步骤2 建立如 liquibase.png 的文件夹


# 步骤3 编写changelog-initial.xml 与 master.xml

## 地址  https://www.jianshu.com/p/992eccad149c


