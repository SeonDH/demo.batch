package com.example.batch.configuration.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = DatabaseConfigUtils.REPOSITORY_PACKAGE)
@Configuration
public class CustomDatabaseConfiguration {

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("customDataSource") DataSource dataSource) {
        return DatabaseConfigUtils.entityManagerFactoryBean("custom", dataSource);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return DatabaseConfigUtils.jpaTransactionManager(entityManagerFactory.getObject());
    }
}
