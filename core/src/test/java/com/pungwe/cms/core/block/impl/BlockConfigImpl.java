package com.pungwe.cms.core.block.impl;

import com.pungwe.cms.core.block.BlockConfig;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ian on 07/03/2016.
 */
public class BlockConfigImpl implements BlockConfig<String, BlockConfigImpl> {

	String id;
	String name;
	String theme;
	String region;
	int weight;
	Map<String, Object> settings = new LinkedHashMap<>();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
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
	public int getWeight() {
		return weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public Map<String, Object> getSettings() {
		return settings;
	}

	@Override
	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}
}
