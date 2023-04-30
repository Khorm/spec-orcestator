package com.petra.lib.z_user_package.source_handlers;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
public class ConfigurationTest {
//    @Autowired
//    private Environment env;

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabase(Database.POSTGRESQL);
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        entityManagerFactoryBean.setPackagesToScan("com.petra.lib");
//
//        return entityManagerFactoryBean;
//    }

//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        System.out.println("hello");
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        System.out.println(adapter);
//        adapter.setDatabase(Database.POSTGRESQL);
//        adapter.setShowSql(true);
//        adapter.setGenerateDdl(false);
//        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
//        return  adapter;
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        System.out.println("DATASOURCE " + dataSource);
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        //emfb.setPersistenceUnitName("test");
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfb.setPackagesToScan("com.petra.lib");
        emfb.setJpaProperties(additionalProperties());
//        EntityManagerFactory emf = emfb.getObject();
        System.out.println("EntityManagerFactory " + emfb);
        return emfb;
    }

    @Bean
    public JpaTransactionManager annotationDrivenTransactionManager(EntityManagerFactory emf) {
        //System.out.println(emf);
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");

        return properties;
    }
}
