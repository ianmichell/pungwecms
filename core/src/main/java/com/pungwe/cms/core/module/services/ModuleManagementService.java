package com.pungwe.cms.core.module.services;

import com.pungwe.cms.core.annotations.Module;
import com.pungwe.cms.core.annotations.ModuleDependency;
import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.persistence.services.PersistenceManagmentService;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Service for managing installed modules. This allows the enabling, disabling and scanning of modules
 * <p>
 * Created by ian on 20/01/2016.
 */
@Service
public class ModuleManagementService {

	private AnnotationConfigApplicationContext moduleContext;

	@Autowired
	private ModuleConfigService<ModuleConfig> configService;

	@Autowired
	private PersistenceManagmentService persistenceManagmentService;

	/**
	 * Enables a module with the given name. If it is already enabled, it will do nothing.
	 *
	 * @param module The name of the module that should be enabled
	 * @return the module
	 * @see com.pungwe.cms.core.annotations.Module
	 * @see com.pungwe.cms.core.module.ModuleConfig
	 */
	public boolean enable(String module) {

		// Fetch the module config
		ModuleConfig config = configService.getModuleConfig(module);

		// Fetch the module class
		try {
			Class<?> c = Class.forName(config.getEntryPoint());
			// Fetch module annotation
			Module ma = c.getAnnotation(Module.class);

			// Enable dependencies
			for (ModuleDependency dependency : ma.dependencies()) {
				// Enable the dependency
				if (!enable(dependency.value())) {
					// FIXME: Add logging as to why it could not enable the dependency
					return false;
				}
			}

			configService.setModuleEnabled(module, true);

			return true;
		} catch (ClassNotFoundException e) {
			// FIXME: Add logging here
			return false;
		}
	}

	public boolean disable(String module) {
		return false;
	}

	public void startEnabledModules() {

		if (moduleContext != null && moduleContext.isActive()) {
			moduleContext.close();
		}

		// Create a new events
		moduleContext = new AnnotationConfigApplicationContext();
		// Set the parent to the root application events
		moduleContext.setParent(persistenceManagmentService.getPersistenceContext());

		// Get a list of enabled modules
		Set<ModuleConfig> enabled = configService.listEnabledModules();
		enabled.forEach(config -> {
			try {
				Class<?> c = Class.forName(config.getEntryPoint());
				moduleContext.register(c);
			} catch (ClassNotFoundException e) {
				return;
			}
		});

		// Refresh the events
		moduleContext.refresh();
	}

	public ApplicationContext getModuleContext() {
		return moduleContext;
	}

	/**
	 * Helper method to determine if a module exists and is enabled
	 *
	 * @param module the name of the module
	 * @return true if the module exists and is enabled
	 */
	public boolean exists(String module) {
		ModuleConfig config = configService.getModuleConfig(module);
		if (config != null) {
			try {
				Class c = Class.forName(config.getEntryPoint());
				return moduleContext.getBeanNamesForType(c).length > 0;
			} catch (ClassNotFoundException ex) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Scans the classpath for classes with the <b>@Module</b> annotation
	 *
	 * @see com.pungwe.cms.core.annotations.Module @Module
	 */
	public void scan() {

		// Important to remove all missing modules first, so scanning the classpath is quicker
		removeMissingModules();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Module.class));
		Set<BeanDefinition> modules = scanner.findCandidateComponents("*");
		modules.forEach(b -> {
			try {
				Class c = Class.forName(b.getBeanClassName());
				configService.registerModule(c, c.getResource("/"));
			} catch (ClassNotFoundException e) {
				// We shouldn't have an issue with this.
				// FIXME: We should add logging this, but it should not be a case of killing the app
			}
		});
	}

	public void removeMissingModules() {
		Set<String> missing = configService.listAllModules().stream().filter(m -> {
			try {
				Class c = Class.forName(m.getEntryPoint());
				// If annotation is present, then we return false.
				return !c.isAnnotationPresent(Module.class);
			} catch (ClassNotFoundException e) {
				return true;
			}
		}).map(m -> m.getName()).collect(Collectors.toSet());
		configService.removeModules(missing);
	}

}
