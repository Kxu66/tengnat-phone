package com.tengnat.assistant.config;

import com.tengnat.assistant.data.properties.DataBaseProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class DefaultDataSourceConfig {

    @Autowired
    private DataBaseProperties dataBaseProperties;

    /**
     * 默认数据源
     */
    @Bean
    public DynamicDataSource dynamicDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(this.dataBaseProperties.getDriverClassName());
        dataSource.setJdbcUrl(this.dataBaseProperties.getUrl());
        dataSource.setUsername(this.dataBaseProperties.getUsername());
        dataSource.setPassword(this.dataBaseProperties.getPassword());
//        dataSource.setMinimumIdle(this.dataBaseProperties.getHikari().getMinimumIdle());
        dataSource.setMaximumPoolSize(this.dataBaseProperties.getHikari().getMaximumPoolSize());
        DynamicDataSource dynamicDatasouce = new DynamicDataSource();
        dynamicDatasouce.setTargetDataSources(Collections.emptyMap());
        dynamicDatasouce.setDefaultTargetDataSource(dataSource);
        return dynamicDatasouce;
    }
}