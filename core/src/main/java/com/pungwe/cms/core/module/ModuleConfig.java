package com.pungwe.cms.core.module;

import java.net.URL;
import java.util.Map;

/**
 * Created by ian on 11/01/2016.
 */
public interface ModuleConfig {

	String getName();

	void setName(String name);

	public String getVersion();

	void setVersion(String version);

	String getEntryPoint();

	void setEntryPoint(String entryPoint);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	String getModuleLocation();
	void setModuleLocation(String url);

	Map<String, Object> getSettings();

	void setSettings(Map<String, Object> settings);

	boolean isInstalled();

	void setInstalled(boolean installed);
}
