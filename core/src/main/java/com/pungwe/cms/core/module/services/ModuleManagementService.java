package com.pungwe.cms.core.module.services;

import com.pungwe.cms.core.annotations.stereotypes.Module;
import com.pungwe.cms.core.annotations.system.ModuleDependency;
import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.utils.CommonHooks;
import com.pungwe.cms.core.utils.services.HookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
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

	private ApplicationContext moduleContext;

	@Autowired
	private ModuleConfigService moduleConfigService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private HookService hookService;

	protected ModuleConfigService<ModuleConfig> getModuleConfigService() {
		return moduleConfigService;
	}

	/**
	 * Enables a module with the given name. If it is already enabled, it will do nothing.
	 *
	 * @param module The name of the module that should be enabled
	 * @return the module
	 * @see Module
	 * @see com.pungwe.cms.core.module.ModuleConfig
	 */
	public boolean enable(String module) {

		// Fetch the module config
		ModuleConfig config = getModuleConfigService().getModuleConfig(module);

		return enable(config);
	}

	public void enable(Collection<String> modules) {
		for (String module : modules) {
			enable(module);
		}
	}

	protected boolean enable(ModuleConfig config) {
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

			getModuleConfigService().setModuleEnabled(ma.name(), true);

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

		assert moduleContext != null;

		// Get a list of enabled modules
		Set<ModuleConfig> enabled = getModuleConfigService().listEnabledModules();

		final List<Class<?>> moduleClasses = new LinkedList<>();
		enabled.forEach(config -> {
			try {
				Class<?> c = Class.forName(config.getEntryPoint());
				moduleClasses.add(c);
				// Execute hook install
			} catch (ClassNotFoundException e) {
				return;
			}
		});

		if (moduleContext instanceof AnnotationConfigEmbeddedWebApplicationContext) {
			((AnnotationConfigEmbeddedWebApplicationContext) moduleContext).register(moduleClasses.toArray(new Class<?>[0]));
		} else if (moduleContext instanceof AnnotationConfigWebApplicationContext) {
			((AnnotationConfigWebApplicationContext) moduleContext).register(moduleClasses.toArray(new Class<?>[0]));
		} else if (moduleContext instanceof AnnotationConfigApplicationContext) {
			((AnnotationConfigApplicationContext) moduleContext).register(moduleClasses.toArray(new Class<?>[0]));
		} else {
			throw new IllegalArgumentException("Application context is not annotation based");
		}
	}

	// Execute hooks
	@EventListener
	public void onRefreshedEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getId() != "module-application-context") {
			return;
		}

		Set<ModuleConfig> enabled = getModuleConfigService().listEnabledModules();
		// Execute hook install
		enabled.stream().filter(moduleConfig -> !moduleConfig.isInstalled()).forEach(config -> {
			try {
				Class<?> c = Class.forName(config.getEntryPoint());
				// Execute hook install
				hookService.executeHook(applicationContext, c, CommonHooks.INSTALL);

				moduleConfigService.setInstalled(config.getName(), true);

			} catch (ClassNotFoundException e) {
				LOG.error("Missing module found: " + config.getEntryPoint(), e);
				moduleConfigService.removeModules(config.getName());
				return;
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			} catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
		});
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
				return applicationContext.getBeanNamesForType(c).length > 0;
			} catch (ClassNotFoundException ex) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Scans the classpath for classes with the <b>@Module</b> annotation
	 *
	 * @see Module @Module
	 */
	public void scan() {
		List<String> defaultEnabledModules = applicationContext.getBean("defaultEnabledModules", List.class);
		// Important to remove all missing modules first, so scanning the classpath is quicker
		removeMissingModules();
		// If we have enabled modules then we should not attempt to enable those in the config file...
		final boolean enabledModules = !getModuleConfigService().listEnabledModules().isEmpty();
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

		if (!enabledModules && defaultEnabledModules != null && !defaultEnabledModules.isEmpty()) {
			enable(defaultEnabledModules);
		}
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

	public ApplicationContext getModuleContext() {
		return moduleContext;
	}

	public void setModuleContext(ApplicationContext moduleContext) {
		this.moduleContext = moduleContext;
	}
}
