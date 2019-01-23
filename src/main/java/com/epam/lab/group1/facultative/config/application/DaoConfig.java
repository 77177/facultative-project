package com.epam.lab.group1.facultative.config.application;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.epam.lab.group1.facultative.persistance")
public class DaoConfig {

    @Bean(name = "dataSource")
    public DataSource H2DataSource() {
        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setName("Facultative")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_script.sql")
                .addScript("fill_script.sql")
                .build();
        return embeddedDatabase;
    }

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = new LocalSessionFactoryBuilder(H2DataSource())
                .scanPackages("com.epam.lab.group1.facultative.model")
                .addProperties(getHibernateProperties())
                .buildSessionFactory();
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }
}
