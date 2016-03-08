package com.pungwe.cms.core.block;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
public interface BlockConfig<ID extends Serializable, T extends BlockConfig> {

	ID getId();
	void setId(ID id);

	String getName();
	void setName(String name);

	String getTheme();
	void setTheme(String theme);

	String getRegion();
	void setRegion(String region);

	int getWeight();
	void setWeight(int weight);

	Map<String, Object> getSettings();
	void setSettings(Map<String, Object> settings);

}
