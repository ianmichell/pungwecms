package com.pungwe.cms.jpa.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by ian on 09/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "jpa")
@SpringApplicationConfiguration(TestEntityDefinitionImpl.AppConfig.class)
public class TestEntityDefinitionImpl {

	@PersistenceUnit
	protected EntityManagerFactory entityManagerFactory;
	@Autowired
	DataSource ds;
	@Autowired
	Environment env;

	@Test
	public void testCreateAndStoreDefinition() throws Exception {
		EntityDefinitionImpl d = new EntityDefinitionImpl();
		d.setId(new EntityTypeInfoImpl("node", "basic_page"));
		Map<String, String> settings = new HashMap<String, String>();
		settings.put("setting", "setting_value");
		d.setConfig(settings);
		String[] profiles = env.getActiveProfiles();
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();

		EntityDefinitionImpl result = em.find(EntityDefinitionImpl.class, new EntityTypeInfoImpl("node", "basic_page"));
		assertNotNull(result);
		assertEquals("setting_value", result.getConfig().get("setting"));
	}

	@Configuration
	@EnableAutoConfiguration
	@ComponentScan(basePackages = "com.pungwe.cms.jpa")
	public static class AppConfig {

	}
}
