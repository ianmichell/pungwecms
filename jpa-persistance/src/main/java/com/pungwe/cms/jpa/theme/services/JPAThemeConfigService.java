package com.pungwe.cms.jpa.theme.services;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.theme.services.ThemeConfigService;
import com.pungwe.cms.jpa.theme.ThemeConfigImpl;
import com.pungwe.cms.jpa.theme.repository.ThemeConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ian on 13/02/2016.
 */
@Service
public class JPAThemeConfigService implements ThemeConfigService<ThemeConfigImpl> {

	@Autowired
	private ThemeConfigRepository themeConfigRepository;

	@Override
	public void registerTheme(Class<?> entryPoint, URL themeLocation) {

		ThemeConfigImpl config = new ThemeConfigImpl();

		Theme themeInfo = entryPoint.getAnnotation(Theme.class);

		if (isEnabled(themeInfo.name())) {
			config = getTheme(themeInfo.name());
			config.setEntryPoint(entryPoint.getName());
		}

		config.setName(themeInfo.name());
		config.setThemeLocation(themeLocation.getFile());
		config.setEnabled(false);
		config.setEntryPoint(entryPoint.getName());

		themeConfigRepository.save(config);
	}

	@Override
	public void removeThemes(String... themes) {
		removeThemes(Arrays.asList(themes));
	}

	@Override
	public void removeThemes(Collection<String> themes) {
		assert !themes.isEmpty();
		themeConfigRepository.deleteByNameIn(themes);
	}

	@Override
	public void setThemeEnabled(String themeName, boolean enabled) {
		ThemeConfigImpl config = getTheme(themeName);
		config.setEnabled(enabled);
		themeConfigRepository.save(config);
	}

	@Override
	public boolean isEnabled(String theme) {
		ThemeConfigImpl config = getTheme(theme);
		return config.isEnabled();
	}

	@Override
	public Set<ThemeConfigImpl> listAllThemes() {
		return themeConfigRepository.findAll().stream().collect(Collectors.toSet());
	}

	@Override
	public Set<ThemeConfigImpl> listEnabledThemes() {
		return themeConfigRepository.findAllByEnabled(true).stream().collect(Collectors.toSet());
	}

	@Override
	public ThemeConfigImpl getDefaultTheme() {
		return null;
	}

	@Override
	public ThemeConfigImpl getDefaultAdminTheme() {
		return null;
	}

	@Override
	public ThemeConfigImpl getTheme(String name) {
		return themeConfigRepository.findOne(name);
	}
}
