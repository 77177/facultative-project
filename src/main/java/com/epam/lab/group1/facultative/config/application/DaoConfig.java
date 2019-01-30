package com.epam.lab.group1.facultative.config.application;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.epam.lab.group1.facultative.persistance")
public class DaoConfig {

    @Bean(name = "H2DataSource")
    public DataSource H2DataSource() {
        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
            .setName("Facultative")
            .setType(EmbeddedDatabaseType.H2)
            .addScript("create_script.sql")
            .addScript("fill_script.sql")
            .build();
        return embeddedDatabase;
    }

    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource() {
        BasicDataSource dataSourceConfig = new BasicDataSource();
        dataSourceConfig.setDriverClassName("org.postgresql.Driver");
        dataSourceConfig.setUrl("jdbc:postgresql://35.246.157.173:5432/facultative");
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setPassword("admin");
        return dataSourceConfig;
    }

    @Bean(name = "H2SessionFactory")
    public SessionFactory H2sessionFactory() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        SessionFactory sessionFactory = new LocalSessionFactoryBuilder(H2DataSource())
            .scanPackages("com.epam.lab.group1.facultative.model")
            .addProperties(properties)
            .buildSessionFactory();
        return sessionFactory;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL81Dialect");
        SessionFactory sessionFactory = new LocalSessionFactoryBuilder(postgresDataSource())
            .scanPackages("com.epam.lab.group1.facultative.model")
            .addProperties(properties)
            .buildSessionFactory();
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}