package cn.citrus.management.factory;


import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DataSourceFactory {
    @Bean
    @Primary
    DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        String jdbcTemplate = StrUtil.format("jdbc:mysql://{}:{}", "localhost", 3306);
        druidDataSource.setUrl(jdbcTemplate);
        return druidDataSource;
    }
}
