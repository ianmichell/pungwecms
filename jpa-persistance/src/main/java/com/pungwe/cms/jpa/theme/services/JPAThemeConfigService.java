package com.pungwe.cms.jpa.theme.services;

import com.pungwe.cms.core.annotations.stereotypes.Theme;
import com.pungwe.cms.core.theme.services.ThemeConfigService;
import com.pungwe.cms.jpa.theme.ThemeConfigImpl;
import com.pungwe.cms.jpa.theme.repository.ThemeConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
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
	public ThemeConfigImpl registerTheme(Class<?> entryPoint, URL themeLocation) {

		ThemeConfigImpl config = new ThemeConfigImpl();

		Theme themeInfo = entryPoint.getAnnotation(Theme.class);

		if (isEnabled(themeInfo.name())) {
			config = getTheme(themeInfo.name());
			config.setEntryPoint(entryPoint.getName());
		} else {
			config.setEnabled(false);
		}

		config.setName(themeInfo.name());
		config.setThemeLocation(themeLocation.toExternalForm());
		config.setEntryPoint(entryPoint.getName());

		return themeConfigRepository.save(config);
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
		if (StringUtils.isEmpty(theme)) {
			return false;
		}
		ThemeConfigImpl config = getTheme(theme);
		return config != null && config.isEnabled();
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
		return themeConfigRepository.findDefaultTheme().orElse(null);
	}

	@Override
	public ThemeConfigImpl getDefaultAdminTheme() {
		return themeConfigRepository.findDefaultAdminTheme().orElse(null);
	}

	@Override
	public ThemeConfigImpl getTheme(String name) {
		return themeConfigRepository.findOne(name);
	}

	@Transactional
	@Override
	public void setInstalled(String name, boolean b) {
		ThemeConfigImpl config = getTheme(name);
		if (config != null) {
			config.setInstalled(b);
			themeConfigRepository.save(config);
		}
	}

	@Override
	@Transactional
	public void setDefaultAdminTheme(String theme) {
		ThemeConfigImpl oldDefaultTheme = getDefaultAdminTheme();
		if (oldDefaultTheme != null) {
			oldDefaultTheme.setDefaultTheme(false);
			themeConfigRepository.save(oldDefaultTheme);
		}

		ThemeConfigImpl newDefaultTheme = getTheme(theme);
		newDefaultTheme.setDefaultAdminTheme(true);

		themeConfigRepository.save(newDefaultTheme);
	}

	@Override
	@Transactional
	public void setDefaultTheme(String theme) {
		ThemeConfigImpl oldDefaultTheme = getDefaultTheme();
		if (oldDefaultTheme != null) {
			oldDefaultTheme.setDefaultTheme(false);
			themeConfigRepository.save(oldDefaultTheme);
		}

		ThemeConfigImpl newDefaultTheme = getTheme(theme);
		newDefaultTheme.setDefaultTheme(true);

		themeConfigRepository.save(newDefaultTheme);
	}
}
