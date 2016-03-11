package com.pungwe.cms.core.theme.services.impl;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.core.theme.services.ThemeConfigService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 13/02/2016.
 */
@Service
public class ThemeConfigServiceImpl implements ThemeConfigService<ThemeConfigImpl> {

	static Set<ThemeConfigImpl> themes = new HashSet<>();

	static {
		ThemeConfigImpl missing = new ThemeConfigImpl();
		missing.setName("missing");
		missing.setEnabled(true);
		missing.setEntryPoint("com.pungwe.cms.themes.missing.MissingTheme");
		missing.setThemeLocation("file://somewhere");
		missing.setVersion("1.0.0-SNAPSHOT");
		themes.add(missing);
	}

	@Override
	public ThemeConfigImpl registerTheme(Class<?> entryPoint, URL themeLocation) {

		ThemeConfigImpl config = new ThemeConfigImpl();

		Theme themeInfo = entryPoint.getAnnotation(Theme.class);
		config.setName(themeInfo.name());
		config.setThemeLocation(themeLocation.getFile());
		config.setEnabled(false);
		config.setEntryPoint(entryPoint.getName());

		themes.add(config);

		return config;
	}

	@Override
	public void removeThemes(String... themes) {
		removeThemes(Arrays.asList(themes));
	}

	@Override
	public void removeThemes(Collection<String> themes) {
		themes.forEach(i -> ThemeConfigServiceImpl.themes.removeIf(t -> t.getName().equalsIgnoreCase(i)));
	}

	@Override
	public void setThemeEnabled(String themeName, boolean enabled) {
		ThemeConfigImpl config = getTheme(themeName);
		config.setEnabled(enabled);
	}

	@Override
	public boolean isEnabled(String theme) {
		ThemeConfigImpl config = getTheme(theme);
		return config != null && config.isEnabled();
	}

	@Override
	public void setDefaultAdminTheme(String theme) {
		listAllThemes().parallelStream().forEach(t -> t.setDefaultAdminTheme(false));
		ThemeConfig config = getTheme(theme);
		if (config != null) {
			config.setDefaultAdminTheme(true);
		}
	}

	@Override
	public void setDefaultTheme(String theme) {
		listAllThemes().parallelStream().forEach(t -> t.setDefaultTheme(false));
		ThemeConfig config = getTheme(theme);
		if (config != null) {
			config.setDefaultTheme(true);
		}
	}

	@Override
	public void setInstalled(String name, boolean b) {
		ThemeConfig config = getTheme(name);
		if (config != null) {
			config.setInstalled(b);
		}
	}

	@Override
	public Set<ThemeConfigImpl> listAllThemes() {
		return themes;
	}

	@Override
	public Set<ThemeConfigImpl> listEnabledThemes() {
		return listAllThemes().stream().filter(t -> t.isEnabled()).collect(Collectors.toSet());
	}

	@Override
	public ThemeConfigImpl getDefaultTheme() {
		return listEnabledThemes().parallelStream().filter(t -> t.isDefaultTheme()).findFirst().orElse(null);
	}

	@Override
	public ThemeConfigImpl getDefaultAdminTheme() {
		return listEnabledThemes().parallelStream().filter(t -> t.isDefaultAdminTheme()).findFirst().orElse(null);
	}

	@Override
	public ThemeConfigImpl getTheme(String name) {
		Optional<ThemeConfigImpl> config = listAllThemes().parallelStream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst();
		return config.orElse(null);
	}
}
