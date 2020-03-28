package com.example.batch.configuration.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableJpaRepositories
@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
public class DataSourceConfig {


    @Value("${external.jdbc.driver.class.name}")
    private String externalDriverClassName;
    @Value("${external.jdbc.url}")
    private String externalJdbcUrl;
    @Value("${external.jdbc.username}")
    private String externalJdbcUsername;
    @Value("${external.jdbc.password}")
    private String externalJdbcPassword;

    @Value("${custom.jdbc.driver.class.name}")
    private String customDriverClassName;
    @Value("${custom.jdbc.url}")
    private String customJdbcUrl;
    @Value("${custom.jdbc.username}")
    private String customJdbcUsername;
    @Value("${custom.jdbc.password}")
    private String customJdbcPassword;


    @Value("${default.jdbc.driver.class.name}")
    private String defaultDriverClassName; // org.h2.Driver
    @Value("${default.jdbc.url}")
    private String defaultJdbcUrl; // jdbc:h2:~/apps/h2db/crm;AUTO_SERVER=TRUE
    @Value("${default.jdbc.username}")
    private String defaultJdbcUsername; // sa
    @Value("${default.jdbc.password}")
    private String defaultJdbcPassword; //

    @Bean
    public DataSource externalDataSource() {
        return getDataSource(externalDriverClassName, externalJdbcUrl, externalJdbcUsername, externalJdbcPassword);
    }

    @Bean
    public DataSource customDataSource() {
        return getDataSource(customDriverClassName, customJdbcUrl, customJdbcUsername, customJdbcPassword);
    }

    @Primary
    @Bean
    public DataSource defaultDataSource() {
        return getDataSource(defaultDriverClassName, defaultJdbcUrl, defaultJdbcUsername, defaultJdbcPassword);
    }

    private DataSource getDataSource(String externalDriverClassName, String externalJdbcUrl, String externalJdbcUsername, String externalJdbcPassword) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(externalDriverClassName);
        dataSource.setUrl(externalJdbcUrl);
        dataSource.setUsername(externalJdbcUsername);
        dataSource.setPassword(externalJdbcPassword);

        return dataSource;
    }


}
