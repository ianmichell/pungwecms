package com.pungwe.cms.jpa.block;

import com.pungwe.cms.core.block.BlockConfig;
import com.pungwe.cms.jpa.converter.HashMapBinaryJSONConverter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
@Entity
@Table(name="blocks", uniqueConstraints = {@UniqueConstraint(name = "name_theme_unique", columnNames = {"name", "theme"})})
public class BlockConfigImpl implements BlockConfig<String, BlockConfigImpl> {

	protected String id;
	protected String name;
	protected String theme;
	protected String region;
	protected int weight;
	protected Map<String, Object> settings;

	@Id
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	@NotBlank
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@NotBlank
	public String getTheme() {
		return theme;
	}

	@Override
	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	@Column(name = "weight")
	public int getWeight() {
		return weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	@Column(name = "settings")
	@Convert(converter = HashMapBinaryJSONConverter.class)
	public Map<String, Object> getSettings() {
		if (settings == null) {
			settings = new HashMap<>();
		}
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

		BlockConfigImpl that = (BlockConfigImpl) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
