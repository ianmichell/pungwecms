package com.pungwe.cms.jpa.config;

import com.pungwe.cms.core.annotations.stereotypes.PersistenceDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// FIXME: This should become a module

/**
 * Created by ian on 09/12/2015.
 */
@PersistenceDriver("jpa")
@ComponentScan("com.pungwe.cms.jpa")
@EnableTransactionManagement
@EnableJpaRepositories("com.pungwe.cms.jpa")
@Import({
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
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
	@ConfigurationProperties(prefix = "jpa.properties.hibernate")
	public Map<String, String> hibernateConfiguration() {
		return new HashMap<String, String>();
	}

	@Bean()
	@ConfigurationProperties(prefix = "jpa.properties.datasource")
	public Map<String, String> datasourceConfiguration() {
		return new HashMap<String, String>();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder factoryBuilder, DataSource dataSource) {
		Map<String, String> hibernateProperties = mapPropertyNames("hibernate", hibernateConfiguration());
		return factoryBuilder.dataSource(dataSource).packages("com.pungwe.cms.jpa.entity", "com.pungwe.cms.jpa.module", "com.pungwe.cms.jpa.theme", "com.pungwe.cms.jpa.block")
				.properties(hibernateProperties).build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean(name = "dataSource")
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
		Map<String, String> toMap = new HashMap<>();
		for (Map.Entry<String, ?> e : map.entrySet()) {
			Object value = e.getValue();
			String key = e.getKey();
			if (value instanceof Map) {
				toMap.putAll(mapPropertyNames(prefix + "." + key, (Map) value));
				continue;
			}
			toMap.put(prefix + "." + key, value + "");
		}
		return toMap;
	}
}
