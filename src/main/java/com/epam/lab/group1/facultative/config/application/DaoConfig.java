package com.epam.lab.group1.facultative.config.application;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.epam.lab.group1.facultative.persistance")
public class DaoConfig {

    @Bean(name = "dataSource")
    public DataSource H2DataSource() {
        EmbeddedDatabaseFactoryBean databaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        databaseFactoryBean.setDatabaseName("Facultative");
        databaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);

        ClassPathResource createScript = new ClassPathResource("classpath:create_script.sql");
        ClassPathResource fillScriptResource = new ClassPathResource("classpath:fill_script.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(createScript, fillScriptResource);
        databaseFactoryBean.setDatabasePopulator(databasePopulator);
        return databaseFactoryBean.getObject();
    }

    @Bean(name = "sessionFactory")
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(H2DataSource());
        sessionFactoryBean.setPackagesToScan("com.epam.lab.group1.facultative.model");
        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
        return sessionFactoryBean.getObject();
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
