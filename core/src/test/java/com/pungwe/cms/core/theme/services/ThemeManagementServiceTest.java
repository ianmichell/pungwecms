package com.pungwe.cms.core.theme.services;

import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.core.module.services.ModuleConfigService;
import com.pungwe.cms.core.module.services.ModuleManagementService;
import com.pungwe.cms.core.module.services.impl.ModuleConfigImpl;
import com.pungwe.cms.core.module.services.impl.ModuleConfigServiceImpl;
import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.core.theme.services.impl.ThemeConfigServiceImpl;
import com.pungwe.cms.modules.test.TestModule;
import com.pungwe.cms.themes.parent.ThemeWithParent;
import com.pungwe.cms.themes.test.TestTheme;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 13/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ThemeManagementServiceTest.AppConfig.class)
@WebAppConfiguration("src/main/resources")
public class ThemeManagementServiceTest {

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

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Autowired
	ThemeConfigService<ThemeConfig> themeConfigService;

	@Autowired
	ThemeManagementService themeManagementService;

	@Autowired
	ModuleConfigService<ModuleConfig> moduleConfigService;

	@Autowired
	ModuleManagementService moduleManagementService;

	@Before
	public void setupModuleContext() {
		AnnotationConfigWebApplicationContext moduleContext = new AnnotationConfigWebApplicationContext();
		moduleContext.setServletContext(wac.getServletContext());
		moduleContext.setId("module-application-context");
		moduleContext.setParent(wac);
		moduleManagementService.setModuleContext(moduleContext);
	}

	@Test
	public void testScan() throws Exception {

		// Scan the classpath for themes
		themeManagementService.scan();

		Set<ThemeConfig> themes = themeConfigService.listAllThemes();

		assertEquals("There should only be two themes found", 2, themes.size());

		assertNotNull("Cannot find test_theme", themeConfigService.getTheme("test_theme"));
	}

	@Test
	public void testEnableTheme() {

		((AnnotationConfigWebApplicationContext)moduleManagementService.getModuleContext()).refresh();

		// Scan for themes
		themeConfigService.registerTheme(TestTheme.class, TestTheme.class.getResource("/"));

		// Enable the theme
		assertTrue("Could not enable test_theme", themeManagementService.enable("test_theme"));

		// Start the theme
		themeManagementService.startEnabledThemes();

		ApplicationContext ctx = themeManagementService.getThemeContext("test_theme");
		TestTheme theme = ctx.getBean(TestTheme.class);
		assertNotNull("theme was null", theme);
	}

	@Test
	public void testEnableThemeWithParent() {

		((AnnotationConfigWebApplicationContext)moduleManagementService.getModuleContext()).refresh();

		// Register the appropriate themes
		themeConfigService.registerTheme(TestTheme.class, TestTheme.class.getResource("/"));
		themeConfigService.registerTheme(ThemeWithParent.class, ThemeWithParent.class.getResource("/"));

		// Ensure they are both disabled to begin with
		themeConfigService.setThemeEnabled("test_theme", false);
		themeConfigService.setThemeEnabled("theme_with_parent", false);

		// Enable them through the management service
		assertTrue("Could not enable theme_with_parent", themeManagementService.enable("theme_with_parent"));

		themeManagementService.startEnabledThemes();

		ApplicationContext ctx = themeManagementService.getThemeContext("theme_with_parent");
		TestTheme parent = ctx.getBean(TestTheme.class);
		assertNotNull("Parent was null!", parent);
		ThemeWithParent child = ctx.getBean(ThemeWithParent.class);
		assertNotNull("Theme with parent was null", child);
	}

	@Test
	public void testThemePathNormal() throws Exception {

		moduleConfigService.registerModule(TestModule.class, TestModule.class.getProtectionDomain().getCodeSource().getLocation());

		moduleManagementService.enable("test_module");

		moduleManagementService.startEnabledModules();

		((AnnotationConfigWebApplicationContext)moduleManagementService.getModuleContext()).refresh();

		// Register the appropriate themes
		themeConfigService.registerTheme(TestTheme.class, TestTheme.class.getProtectionDomain().getCodeSource().getLocation());
		themeConfigService.registerTheme(ThemeWithParent.class, ThemeWithParent.class.getProtectionDomain().getCodeSource().getLocation());
		// Ensure they are both disabled to begin with
		themeConfigService.setThemeEnabled("test_theme", true);
		themeConfigService.setThemeEnabled("theme_with_parent", true);

		ThemeConfig theme = themeConfigService.getTheme("theme_with_parent");
		theme.setDefaultTheme(true);

		// Remove missing...
		themeManagementService.removeMissingThemes();

		themeManagementService.startEnabledThemes();

		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
		List<String> result = themeManagementService.resolveViewPath(request, "classpath:/templates/", "my_view", ".twig");
		assertEquals("There should be two results", 4, result.size());
		assertEquals("Hook template was not in the list", "classpath:/templates/theme_with_parent/my_view.twig", result.get(0));
		assertEquals("Parent Hook template was not in the list", "classpath:/templates/my_theme/my_view.twig", result.get(1));
		assertEquals("Parent Hook template was not in the list", "classpath:/templates/test_module/my_view.twig", result.get(2));
		assertEquals("Default template was not in the list", "classpath:/templates/my_view.twig", result.get(3));
	}
}
