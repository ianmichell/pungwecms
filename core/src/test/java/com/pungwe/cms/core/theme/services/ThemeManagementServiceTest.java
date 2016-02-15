package com.pungwe.cms.core.theme.services;

import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.core.theme.services.impl.ThemeConfigServiceImpl;
import com.pungwe.cms.themes.parent.ThemeWithParent;
import com.pungwe.cms.themes.test.TestTheme;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ian on 13/02/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ThemeManagementServiceTest.AppConfig.class)
public class ThemeManagementServiceTest {

	@Configuration
	@ComponentScan(basePackages = {"com.pungwe.cms.core"})
	@PropertySource("classpath:/application.yml")
	public static class AppConfig {

		@Bean
		public ThemeConfigService themeConfigService() {
			return new ThemeConfigServiceImpl();
		}

	}

	@Autowired
	ThemeConfigService<ThemeConfig> themeConfigService;

	@Autowired
	ThemeManagementService themeManagementService;

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
}
