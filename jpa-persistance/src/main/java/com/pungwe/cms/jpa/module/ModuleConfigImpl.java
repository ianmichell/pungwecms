package com.pungwe.cms.jpa.module;

import com.pungwe.cms.core.module.ModuleConfig;
import com.pungwe.cms.jpa.converter.HashMapBinaryJSONConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 22/01/2016.
 */
@Entity
@Table(name="modules")
public class ModuleConfigImpl implements ModuleConfig {

	@Id
	protected String name;
	protected String version;
	@Column(name="entry_point", nullable = false)
	@NotBlank
	protected String entryPoint;
	protected boolean enabled;
	@Column(name="module_location")
	protected String moduleLocation;
	@Convert(converter = HashMapBinaryJSONConverter.class)
	protected Map<String, Object> settings = new HashMap<>();

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

	@Override
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

	@Override
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
