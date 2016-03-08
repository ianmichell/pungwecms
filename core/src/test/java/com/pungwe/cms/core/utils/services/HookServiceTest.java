package com.pungwe.cms.core.utils.services;

import com.pungwe.cms.core.annotations.Hook;
import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.module.services.ModuleConfigService;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.module.services.impl.ModuleConfigServiceImpl;
import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.core.theme.services.ThemeConfigService;
import com.pungwe.cms.core.theme.services.ThemeManagementService;
import com.pungwe.cms.core.theme.services.impl.ThemeConfigServiceImpl;
import com.pungwe.cms.core.utils.HookCallback;
import com.pungwe.cms.modules.test.TestModule;
import com.pungwe.cms.test.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 27/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(HookServiceTest.AppConfig.class)
@WebAppConfiguration("src/main/resources")
public class HookServiceTest extends AbstractWebTest {

	@Configuration
	@ComponentScan(basePackages = {"com.pungwe.cms.core"})
	@PropertySource("classpath:/application.yml")
	public static class AppConfig {

		@Bean
		public ThemeConfigService themeConfigService() {
			return new ThemeConfigServiceImpl();
		}

		@Bean
		public ModuleConfigServiceImpl moduleConfig() { return new ModuleConfigServiceImpl(); }

	}

	@Autowired
	HookService hookService;

	@Autowired
	ModuleConfigService<ModuleConfig> configService;

	@Autowired
	ModuleManagementService managementService;

	@Before
	public void beforeTest() {
		configService.registerModule(TestModule.class, TestModule.class.getProtectionDomain().getCodeSource().getLocation());
		configService.setModuleEnabled("test_module", true);
		managementService.startEnabledModules();
	}

	@Test
	public void testExecuteHookWithoutClass() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook("test_hook", (c, a) -> {
			bool.set(true);
		});

		assertTrue(bool.get());
	}

	@Test
	public void testExecuteHookWithClass() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook(TestModule.class, "test_hook", (c, a) -> {
			bool.set(true);
		});

		assertTrue(bool.get());
	}

	@Test
	 public void testExecuteHookWithClassAndParameter() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook(TestModule.class, "parameter_hook", (c, a) -> {
			if (a.equals("parameter")) {
				bool.set(true);
			} else {
				bool.set(false);
			}
		}, "parameter");

		assertTrue(bool.get());
	}

	@Test
	public void testExecuteHookWithClassAndTooManyParameters() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook(TestModule.class, "parameter_hook", (c, a) -> {
			if (a.equals("parameter1")) {
				bool.set(true);
			} else {
				bool.set(false);
			}
		}, "parameter1", "parameter2");

		assertTrue(bool.get());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExecuteHookWithClassAndInvalidParameterType() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook(TestModule.class, "parameter_hook", (c, a) -> {
			if (a.equals(1)) {
				bool.set(true);
			} else {
				bool.set(false);
			}
		}, 1);
	}

	@Test
	public void testExecuteHookWithClassAndTooFewParameters() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook(TestModule.class, "parameter_many_hook", (c, a) -> {
			if (a.equals("parameter1")) {
				bool.set(true);
			} else {
				bool.set(false);
			}
		}, "parameter1");

		assertFalse(bool.get());
	}

	@Test
	public void testExecuteHookWithParameter() throws InvocationTargetException, IllegalAccessException {
		final AtomicBoolean bool = new AtomicBoolean();

		hookService.executeHook("parameter_hook", (c, a) -> {
			if (a.equals("parameter")) {
				bool.set(true);
			} else {
				bool.set(false);
			}
		}, "parameter");

		assertTrue(bool.get());
	}

	@Test
	public void testExecuteHookWithClassWithoutCallback() throws InvocationTargetException, IllegalAccessException {
		hookService.executeHook(TestModule.class, "no_callback_hook");
	}

	@Test
	public void testExecuteHookWithoutCallback() throws InvocationTargetException, IllegalAccessException {
		hookService.executeHook("no_callback_hook");
	}
}
