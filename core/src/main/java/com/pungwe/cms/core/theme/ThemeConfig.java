package com.pungwe.cms.core.theme;

import java.util.Map;

/**
 * Created by ian on 13/02/2016.
 */
public interface ThemeConfig {

	String getName();
	void setName(String name);

	String getVersion();
	void setVersion(String version);

	boolean isEnabled();
	void setEnabled(boolean enabled);

	boolean isDefaultTheme();
	void setDefaultTheme(boolean defaultTheme);

	boolean isDefaultAdminTheme();
	void setDefaultAdminTheme(boolean adminTheme);

	String getEntryPoint();
	void setEntryPoint(String entryPoint);

	String getThemeLocation();
	void setThemeLocation(String location);

	Map<String, Object> getSettings();
	void setSettings(Map<String, Object> settings);
}
