package com.pungwe.cms.core.module.services.impl;

import com.pungwe.cms.core.module.ModuleConfig;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 21/01/2016.
 */
public class ModuleConfigImpl implements ModuleConfig {

	String name;
	String version;
	String entryPoint;
	boolean enabled;
	boolean installed;
	String moduleLocation;
	Map<String, Object> settings = new HashMap<>();

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
	public String getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
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
	public String getModuleLocation() {
		return moduleLocation;
	}

	public void setModuleLocation(String moduleLocation) {
		this.moduleLocation = moduleLocation;
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
	public boolean isInstalled() {
		return installed;
	}

	@Override
	public void setInstalled(boolean installed) {
		this.installed = installed;
	}

	@Override
	public String toString() {
		return "Module [" +
				"name='" + name + '\'' +
				", version='" + version + '\'' +
				", entryPoint='" + entryPoint + '\'' +
				", enabled=" + enabled +
				", moduleLocation='" + moduleLocation + '\'' +
				']';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ModuleConfigImpl that = (ModuleConfigImpl) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
