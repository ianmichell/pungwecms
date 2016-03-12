package com.pungwe.cms.core.module.services;

import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.module.services.impl.ModuleConfigServiceImpl;
import com.pungwe.cms.modules.dependency.ModuleWithDependency;
import com.pungwe.cms.modules.test.TestComponent;
import com.pungwe.cms.modules.test.TestModule;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 21/01/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuleManagementServiceTest.AppConfig.class)
@WebAppConfiguration(value="src/main/resources")
public class ModuleManagementServiceTest extends AbstractWebTest {

	@Configuration
	@ComponentScan(basePackages = {"com.pungwe.cms.core"})
	@PropertySource("classpath:/application.yml")
	public static class AppConfig {

		@Bean
		public ModuleConfigService moduleConfigService() {
			return new ModuleConfigServiceImpl();
		}

	}

	@Autowired
	ModuleConfigService<ModuleConfig> configService;

	@Autowired
	ModuleManagementService managementService;

	@Test
	public void testScan() {

		// Scan the classpath for modules
		managementService.scan();

		// Ensure that the module we're looking for exists
		Set<ModuleConfig> modules = configService.listAllModules();

		assertEquals("There should be only two modules found", 2, modules.size());

		Optional<ModuleConfig> module = modules.stream().filter(m -> {
			System.out.println(m);
			boolean found = m.getName().equalsIgnoreCase("test_module");
			System.out.println(found);
			return found;
		}).findFirst();
		assertTrue("Module: test_module doesn't exist", module.isPresent());
	}

	@Test
	public void testEnableModule() {
		// Scan for modules
		configService.registerModule(TestModule.class, TestModule.class.getResource("/"));

		// Assert that we have a created the appropriate application events
		assertTrue("Could not enable module", managementService.enable("test_module"));

		// Start the module management events
		managementService.startEnabledModules();
//		webApplicationContext.refresh();

		// Application events for module
		TestComponent tc = managementService.getModuleContext().getBean(TestComponent.class);

		assertNotNull("Test Component was null", tc);
	}

	@Test
	public void testEnabledModuleWithDependency() {
		configService.registerModule(ModuleWithDependency.class, ModuleWithDependency.class.getResource("/"));
		configService.registerModule(TestModule.class, TestModule.class.getResource("/"));

		// Assert that we have a created the appropriate application events
		assertTrue("Could not enable module", managementService.enable("module_with_dependency"));

		// Start all enabled modules
		managementService.startEnabledModules();
//		webApplicationContext.refresh();

		// Check the dependency injection
		ModuleWithDependency dep = managementService.getModuleContext().getBean(ModuleWithDependency.class);
		assertTrue("Could not autowire the testcomponent", dep.isCommponentInjected());
	}

	@Test
	public void testDisableModule() {

	}
}
