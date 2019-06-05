package com.tengnat.assistant.config;

import com.tengnat.assistant.data.constants.DataSourceConstant;
import com.tengnat.assistant.data.properties.DataBaseProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    private DataBaseProperties dataBaseProperties;

    private Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        String targetDataSource = DataSourceHolder.getDataSourceType();
        if (!StringUtils.isEmpty(targetDataSource) && !DataSourceConstant.DEFAULT_DATASOURCE.equals(targetDataSource)) {
            if (!this.dataSourceMap.containsKey(targetDataSource)) {
                HikariDataSource newDataSource = this.createDataSource(targetDataSource);
                if (newDataSource != null) {
                    this.dataSourceMap.put(targetDataSource, newDataSource);
                    super.setTargetDataSources(this.dataSourceMap);
                    super.afterPropertiesSet();
                }
            }
        }
        return targetDataSource;
    }


    private HikariDataSource createDataSource(String targetDataSource) {
        String selectDataSourceSQL = "select dbcid, dbcserver, dbcuser, dbcpwd from com_odbc where dbcid = " + Integer.parseInt(targetDataSource);
        log.info("select data source sql = {}", selectDataSourceSQL);
        try (
                Connection connection = DriverManager.getConnection(this.dataBaseProperties.getUrl(), this.dataBaseProperties.getUsername(), this.dataBaseProperties.getPassword());
                PreparedStatement statement = connection.prepareStatement(selectDataSourceSQL);
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                HikariDataSource dataSource = new HikariDataSource();
                dataSource.setDriverClassName(this.dataBaseProperties.getDriverClassName());
                dataSource.setJdbcUrl(resultSet.getString("dbcserver"));
                dataSource.setUsername(resultSet.getString("dbcuser"));
                dataSource.setPassword(resultSet.getString("dbcpwd"));
                dataSource.setMinimumIdle(this.dataBaseProperties.getHikari().getMinimumIdle());
                dataSource.setMaximumPoolSize(this.dataBaseProperties.getHikari().getMaximumPoolSize());
                return dataSource;
            }
        } catch (Exception e) {
            logger.error("create data source error", e);
        }
        return null;
    }
}

