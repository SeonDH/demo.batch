package com.example.batch.configuration.database;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

public class DatabaseConfigUtils {
    public static final String BASE_PACKAGE = "com.example.batch";
    public static final String ENTITY_PACKAGE = BASE_PACKAGE + ".entity";
    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".repository";


    private DatabaseConfigUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            String persistenceUnitName, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName(persistenceUnitName);
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapters());
        emf.setPackagesToScan(ENTITY_PACKAGE);
        emf.setJpaProperties(jpaProperties());

        return emf;
    }

    public static JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setJpaDialect(new HibernateJpaDialect());

        return jpaTransactionManager;

    }

    private static Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.globally_quoted_identifiers", "true");

        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");

        properties.setProperty("hibernate.jdbc.batch_size", "5000");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");

        properties.setProperty("spring.jpa.hibernate.jdbc.batch_size", "5000");
        properties.setProperty("spring.jpa.hibernate.order_inserts", "true");
        properties.setProperty("spring.jpa.hibernate.order_updates", "true");

        properties.setProperty("spring.jpa.hibernate.jdbc.batch_versioned_data", "true");
        properties.setProperty("spring.jpa.properties.hibernate.jdbc.batch_size", "5000");
        properties.setProperty("spring.jpa.properties.hibernate.order_inserts", "true");
        properties.setProperty("spring.jpa.properties.hibernate.order_updates", "true");
        properties.setProperty("spring.jpa.properties.hibernate.jdbc.batch_versioned_data", "true");
        return properties;
    }

    private static JpaVendorAdapter jpaVendorAdapters() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");

        return hibernateJpaVendorAdapter;
    }

}
