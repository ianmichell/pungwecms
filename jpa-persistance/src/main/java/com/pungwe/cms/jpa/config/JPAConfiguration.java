package com.pungwe.cms.jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.*;

/**
 * Created by ian on 09/12/2015.
 */
@Configuration
@ComponentScan("com.pungwe.cms.jpa")
@EnableTransactionManagement
@ConfigurationProperties(prefix = "jpa")
@Profile("JPA")
public class JPAConfiguration {

    @Value("${jpa.driver.class}")
    protected String driverClassName;

    @Value("${jpa.driver.url}")
    protected String databaseUrl;

    @Value("${jpa.driver.username:}")
    protected String username;

    @Value("${jpa.driver.password:}")
    protected String password;

    @Bean()
    @ConfigurationProperties(prefix="jpa.properties.hibernate")
    public Map<String, String> hibernateConfiguration() {
        return new HashMap<String, String>();
    }

    @Bean()
    @ConfigurationProperties(prefix="jpa.properties.datasource")
    public Map<String, String> datasourceConfiguration() {
        return new HashMap<String, String>();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.pungwe.cms.jpa.entity"});

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(adapter);
        Properties p = new Properties();
        Map<String, String> hibernateProperties = mapPropertyNames("hibernate", hibernateConfiguration());
        p.putAll(hibernateProperties);
        em.setJpaProperties(p);
        em.setPersistenceUnitName("pungweEM");
        return em;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(databaseUrl);

        Properties p = new Properties();
        Map<String, String> datasourceProperties = mapPropertyNames("datasource", hibernateConfiguration());
        p.putAll(datasourceProperties);
        ds.setConnectionProperties(p);
        if (!StringUtils.isEmpty(username)) {
            ds.setUsername(username);
        }
        if (!StringUtils.isEmpty(password)) {
            ds.setPassword(password);
        }
        return ds;
    }

    private Map<String, String> mapPropertyNames(String prefix, Map<String, ?> map) {
        Map<String, String> toMap = new HashMap<String, String>();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value instanceof Map) {
                toMap.putAll(mapPropertyNames(prefix + "." + key, (Map)value));
                continue;
            }
            toMap.put(prefix + "." + key, value + "");
        }
        return toMap;
    }
}
