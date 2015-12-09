package com.pungwe.cms.jpa.entity;

import com.pungwe.cms.core.entity.EntityInstance;
import org.hibernate.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ian on 09/12/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "jpa")
@SpringApplicationConfiguration(TestEntityInstanceImpl.AppConfig.class)
public class TestEntityInstanceImpl {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "com.pungwe.cms.jpa")
    static class AppConfig {

    }

    @PersistenceUnit
    protected EntityManagerFactory entityManagerFactory;

    @Test
    public void testCreateEntityInstance() throws Exception {

        EntityInstanceImpl entity = new EntityInstanceImpl();
        entity.setId(new EntityInstanceIdImpl(UUID.randomUUID().toString(), new EntityTypeInfoImpl("node", "basic_page")));
        Map<String, String> data = new HashMap<String, String>();
        data.put("field", "field_value");
        entity.setData(data);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        EntityInstanceImpl result = entityManager.find(EntityInstanceImpl.class, entity.getId());
        assertNotNull(result);
        assertEquals("field_value", result.getData().get("field"));
    }
}
