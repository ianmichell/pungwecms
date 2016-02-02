package com.pungwe.cms.core.application;

import com.pungwe.cms.core.annotations.PersistenceDriver;
import com.pungwe.cms.core.config.BaseApplicationConfig;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * Created by ian on 20/01/2016.
 */
public class PungweCMSApplication {

	private static Logger logger = LoggerFactory.getLogger(PungweCMSApplication.class);
	public static void start(String[] args) {

		SpringApplicationBuilder builder = new SpringApplicationBuilder();
		// FIXME: This should probably be done in a utility class
		builder.initializers(applicationContext -> {

			// Set the id of the application context
			applicationContext.setId("parent-application-context");

			// Scan for the appropriate persistence driver and register it
			String persistenceType = applicationContext.getEnvironment().getProperty("cms.data.type");
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
			// Scan the classpath for the persistence driver
			scanner.addIncludeFilter(new AnnotationTypeFilter(PersistenceDriver.class));
			Set<BeanDefinition> drivers = scanner.findCandidateComponents("*");
			for (BeanDefinition driver : drivers) {
				Class<?> c = null;
				try {
					c = Class.forName(driver.getBeanClassName());
				} catch (ClassNotFoundException e) {
					throw new IllegalArgumentException(e);
				}
				PersistenceDriver annotation = c.getAnnotation(PersistenceDriver.class);
				if (!annotation.value().equalsIgnoreCase(persistenceType)) {
					continue;
				}

				if (applicationContext instanceof AnnotationConfigEmbeddedWebApplicationContext) {
					((AnnotationConfigEmbeddedWebApplicationContext) applicationContext).register(c);
				} else if (applicationContext instanceof AnnotationConfigApplicationContext) {
					((AnnotationConfigApplicationContext) applicationContext).register(c);
				} else {
					throw new IllegalArgumentException("Application context is not annotation based");
				}
				return;
			}
		}).listeners(event -> {
			/* Scan for modules and enable them */
			if (event instanceof ContextRefreshedEvent && ((ContextRefreshedEvent)event).getApplicationContext().getId().equalsIgnoreCase("parent-application-context")) {
				ModuleManagementService moduleManagementService = ((ContextRefreshedEvent)event).getApplicationContext().getBean(ModuleManagementService.class);
				moduleManagementService.scan();
				moduleManagementService.startEnabledModules();
			}
		}).sources(BaseApplicationConfig.class).registerShutdownHook(true).run(args);
	}

	public static void main(String[] args) {
		start(args);
	}
}
