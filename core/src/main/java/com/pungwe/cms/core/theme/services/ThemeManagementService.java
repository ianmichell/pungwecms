package com.pungwe.cms.core.theme.services;

import com.pungwe.cms.core.annotations.Theme;
import com.pungwe.cms.core.theme.ThemeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 29/01/2016.
 */
@Service
public class ThemeManagementService {

	private static final Logger LOG = LoggerFactory.getLogger(ThemeManagementService.class);

	@Autowired
	private ThemeConfigService<? extends ThemeConfig> themeConfigService;

	@Autowired
	private ApplicationContext applicationContext;

	private Map<String, AnnotationConfigApplicationContext> themeContexts = new TreeMap<>();

	public ApplicationContext getThemeContext(String name) {
		return themeContexts.get(name);
	}

	public boolean enable(String theme) {
		// Fetch the theme config
		ThemeConfig config = themeConfigService.getTheme(theme);
		try {
			Class<?> c = Class.forName(config.getEntryPoint());
			Theme info = c.getAnnotation(Theme.class);
			String parent = info.parent();
			if (!StringUtils.isEmpty(parent) && !themeConfigService.isEnabled(parent) && !enable(parent)) {
				LOG.error("Could not enabled parent theme: " + parent + " for theme: " + theme);
				return false;
			}
			themeConfigService.setThemeEnabled(theme, true);
			return true;
		} catch (ClassNotFoundException ex) {
			LOG.error("Could not enable theme: " + theme, ex);
			themeConfigService.removeThemes(theme);
			return false;
		}
	}

	public boolean disable(String theme) {
		themeConfigService.setThemeEnabled(theme, false);
		AnnotationConfigApplicationContext ctx = themeContexts.remove(theme);
		ctx.close();
		return ctx.isActive();
	}

	public void startEnabledThemes() {

		// Get a list of enabled themes
		Set<ThemeConfig> enabled = (Set<ThemeConfig>)themeConfigService.listEnabledThemes();

		// Create application contexts for the enabled themes. This is different from the module
		// context, whereby the modules share a single application context, themes need to be isolated
		// from each other in order to function correctly...
		enabled.stream().sorted((t1, t2) -> {
			try {
				// First theme class
				Class<?> c1 = Class.forName(t1.getEntryPoint());
				Theme i1 = c1.getAnnotation(Theme.class);
				// Second theme class
				Class<?> c2 = Class.forName(t2.getEntryPoint());
				Theme i2 = c2.getAnnotation(Theme.class);

				// If t1 parent is blank and t2 is not, then t1 should be before t2.
				if (StringUtils.isEmpty(i1.parent()) && !StringUtils.isEmpty(i2.parent())) {
					return -1;
					// If t1 has a parent and t2 does not, then it should be after t2
				} else if (!StringUtils.isEmpty(i1.parent()) && StringUtils.isEmpty(i2.parent())) {
					return 1;
				}
				// Check if t1 is the parent of t2. If it is, then t1 should be first
				if (i1.name().equalsIgnoreCase(i2.parent())) {
					return -1;
					// otherwise t2 should be first if it's the parent of t1
				} else if (i2.name().equalsIgnoreCase(i1.parent())) {
					return 1;
					// Ensure that there is not a circular reference
				} else if (i1.name().equalsIgnoreCase(i2.parent()) && i2.name().equalsIgnoreCase(i1.parent())) {
					throw new IllegalArgumentException("Circular reference in theme parents");
				}
				// Just sort by name by default... If none of the above, then sort by name...
				return t1.getName().compareTo(t2.getName());
			} catch (ClassNotFoundException ex) {
				return -1;
			}
		}).forEachOrdered(theme -> {
			try {
				Class<?> c = Class.forName(theme.getEntryPoint());

				// Check for an existing application context
				AnnotationConfigApplicationContext ctx = (AnnotationConfigApplicationContext) getThemeContext(theme.getName());
				if (ctx != null && ctx.isActive()) {
					ctx.close();
				}

				// Create a new application context for the theme
				ctx = new AnnotationConfigApplicationContext();

				// Fetch the theme info
				Theme themeInfo = c.getAnnotation(Theme.class);

				// Find the parent application context for the theme and set it
				ApplicationContext parent = getThemeContext(themeInfo.parent());
				ctx.setParent(parent);

				// Register the theme entry point class
				ctx.register(c);

				// Refresh the context
				ctx.refresh();

				// Overwrite the existing theme application context
				themeContexts.put(theme.getName(), ctx);

			} catch (ClassNotFoundException ex) {
				LOG.error("Could not start theme: " + theme.getName(), ex);
			}
		});

	}

	public void scan() {

		// Remove the themes missing from the classpath
		removeMissingThemes();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Theme.class));
		Set<BeanDefinition> modules = scanner.findCandidateComponents("*");
		modules.forEach(b -> {
			try {
				Class c = Class.forName(b.getBeanClassName());
				themeConfigService.registerTheme(c, c.getProtectionDomain().getCodeSource().getLocation());
			} catch (ClassNotFoundException e) {
				LOG.error("Could not load a module found on the class path, due to it's class not being found. This should never happen and usually means something is wrong with the environment", e);
			}
		});
	}

	protected void removeMissingThemes() {
		Set<String> missing = themeConfigService.listAllThemes().stream().filter(t -> {
			try {
				Class<?> c = Class.forName(t.getEntryPoint());
				return c.isAnnotationPresent(Theme.class);
			} catch (Exception ex) {
				return true;
			}
		}).map(t -> t.getName()).collect(Collectors.toSet());
		themeConfigService.removeThemes(missing);
	}
}
