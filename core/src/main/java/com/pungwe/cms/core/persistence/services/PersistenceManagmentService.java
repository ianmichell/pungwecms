package com.pungwe.cms.core.persistence.services;

import com.pungwe.cms.core.annotations.PersistenceDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by ian on 23/01/2016.
 */
@Service
public class PersistenceManagmentService implements ApplicationContextAware {

	@Value("${cms.data.type}")
	protected String persistenceType;

	private ApplicationContext context;

	private AnnotationConfigApplicationContext persistenceContext;

	@PostConstruct
	public void postConstruct() {
		persistenceContext = new AnnotationConfigApplicationContext();
		persistenceContext.setId("persistenceContext");
		persistenceContext.setParent(context);
		try {
			scan();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	private void scan() throws ClassNotFoundException {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		// Scan the classpath for the persistence driver
		scanner.addIncludeFilter(new AnnotationTypeFilter(PersistenceDriver.class));
		Set<BeanDefinition> drivers = scanner.findCandidateComponents("*");
		for (BeanDefinition driver : drivers) {
			Class<?> c = Class.forName(driver.getBeanClassName());
			PersistenceDriver annotation = c.getAnnotation(PersistenceDriver.class);
			if (!annotation.value().equalsIgnoreCase(persistenceType)) {
				continue;
			}
			persistenceContext.register(c);
			persistenceContext.refresh();
			return;
		}
	}

	public ApplicationContext getPersistenceContext() {
		return this.persistenceContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
