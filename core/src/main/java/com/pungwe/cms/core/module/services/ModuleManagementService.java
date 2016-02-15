package com.pungwe.cms.core.module.services;

import com.pungwe.cms.core.annotations.Module;
import com.pungwe.cms.core.annotations.ModuleDependency;
import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.utils.CommonHooks;
import com.pungwe.cms.core.utils.services.HookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

// FIXME: Move this to the parent application context if need be
/**
 * Service for managing installed modules. This allows the enabling, disabling and scanning of modules
 * <p>
 * Created by ian on 20/01/2016.
 */
@Service

public class ModuleManagementService {

	private static final Logger LOG = LoggerFactory.getLogger(ModuleManagementService.class);

	private AnnotationConfigApplicationContext moduleContext;

	// FIXME: Never going to work on a different application context :(
	@Autowired
	private ModuleConfigService moduleConfigService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	HookService hookService;

	protected ModuleConfigService<ModuleConfig> getModuleConfigService() {
		return moduleConfigService;
	}

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
		ModuleConfig config = getModuleConfigService().getModuleConfig(module);

		// Fetch the module class
		try {
			Class<?> c = Class.forName(config.getEntryPoint());
			// Fetch module annotation
			Module ma = c.getAnnotation(Module.class);

			// Enable dependencies
			for (ModuleDependency dependency : ma.dependencies()) {
				// Enable the dependency
				if (!enable(dependency.value())) {
					LOG.error("Cannot enable module: " + ma.name() + " due to missing dependencies");
					return false;
				}
			}

			getModuleConfigService().setModuleEnabled(module, true);

			return true;
		} catch (ClassNotFoundException e) {
			LOG.error("Cannot find module class: " + config.getEntryPoint(), e);
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
		moduleContext.setParent(applicationContext);

		// Get a list of enabled modules
		Set<ModuleConfig> enabled = getModuleConfigService().listEnabledModules();
		enabled.forEach(config -> {
			try {
				Class<?> c = Class.forName(config.getEntryPoint());
				moduleContext.register(c);

				// Execute hook install
			} catch (ClassNotFoundException e) {
				return;
			}
		});

		// Refresh the events
		moduleContext.refresh();

		// Execute hook install
		enabled.forEach(config -> {
			try {
				Class<?> c = Class.forName(config.getEntryPoint());
				String name = c.getAnnotation(Module.class).name();
				// Execute hook install
				hookService.executeHook(c, CommonHooks.INSTALL);

				moduleConfigService.setInstalled(name, true);

			} catch (ClassNotFoundException e) {
				return;
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			} catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
		});
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
		ModuleConfig config = getModuleConfigService().getModuleConfig(module);
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
				getModuleConfigService().registerModule(c, c.getProtectionDomain().getCodeSource().getLocation());
			} catch (ClassNotFoundException e) {
				LOG.error("Could not load a module found on the class path, due to it's class not being found. This should never happen and usually means something is wrong with the environment", e);
			}
		});

	}

	public void removeMissingModules() {
		Set<String> missing = getModuleConfigService().listAllModules().stream().filter(m -> {
			try {
				Class c = Class.forName(m.getEntryPoint());
				// If annotation is present, then we return false.
				return !c.isAnnotationPresent(Module.class);
			} catch (ClassNotFoundException e) {
				return true;
			}
		}).map(m -> m.getName()).collect(Collectors.toSet());
		getModuleConfigService().removeModules(missing);
	}

}
