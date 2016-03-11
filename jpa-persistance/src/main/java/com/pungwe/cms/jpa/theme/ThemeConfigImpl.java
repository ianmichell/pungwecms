package com.pungwe.cms.jpa.theme;

import com.pungwe.cms.core.theme.ThemeConfig;
import com.pungwe.cms.jpa.converter.HashMapBinaryJSONConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by ian on 13/02/2016.
 */
@Entity
@Table(name = "themes")
public class ThemeConfigImpl implements ThemeConfig {

	@Id
	private String name;
	private String version;
	private boolean enabled;
	private boolean installed;

	@Column(name="default_admin_theme")
	private boolean defaultAdminTheme;

	@Column(name="default_theme")
	private boolean defaultTheme;

	@Column(name="entry_point", nullable = false)
	@NotBlank
	protected String entryPoint;

	@Column(name = "theme_location")
	private String themeLocation;

	@Convert(converter = HashMapBinaryJSONConverter.class)
	private Map<String, Object> settings;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isInstalled() {
		return installed;
	}

	public void setInstalled(boolean installed) {
		this.installed = installed;
	}

	@Override
	public String getEntryPoint() {
		return entryPoint;
	}

	@Override
	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}

	@Override
	public String getThemeLocation() {
		return themeLocation;
	}

	@Override
	public void setThemeLocation(String themeLocation) {
		this.themeLocation = themeLocation;
	}

	@Override
	public Map<String, Object> getSettings() {
		return settings;
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}

	@Override
	public boolean isDefaultAdminTheme() {
		return defaultAdminTheme;
	}

	@Override
	public void setDefaultAdminTheme(boolean defaultAdminTheme) {
		this.defaultAdminTheme = defaultAdminTheme;
	}

	@Override
	public boolean isDefaultTheme() {
		return defaultTheme;
	}

	@Override
	public void setDefaultTheme(boolean defaultTheme) {
		this.defaultTheme = defaultTheme;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ThemeConfigImpl that = (ThemeConfigImpl) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
