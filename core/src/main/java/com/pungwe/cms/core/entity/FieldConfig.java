package com.pungwe.cms.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ian on 01/12/2015.
 */
public final class FieldConfig implements Comparable<FieldConfig> {

	private int weight;
	private String name;
	private String label;
	private int cardinality;
	private Map<String, Object> settings;
	private String fieldType;
	private String storageType;
	private String formatter;
	private String widget;
	private boolean required;


	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public int getCardinality() {
		return cardinality;
	}


	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}


	public Map<String, Object> getSettings() {
		return settings;
	}


	public void setSettings(Map<String, Object> settings) {
		this.settings = settings;
	}


	public String getFieldType() {
		return fieldType;
	}


	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}


	public String getStorageType() {
		return storageType;
	}


	public void setStorageType(String fieldStorageType) {
		this.storageType = fieldStorageType;
	}


	public String getFormatter() {
		return formatter;
	}


	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}


	public String getWidget() {
		return widget;
	}


	public void setWidget(String widget) {
		this.widget = widget;
	}


	public boolean isRequired() {
		return required;
	}


	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Compared by weight, if they are equal they are sorted by name.
	 *
	 * @param o
	 * @return the comparison
	 */

	public int compareTo(FieldConfig o) {
		int name = this.getName().compareTo(o.getName());
		if (name == 0) {
			return name;
		}
		int weight = Integer.compare(this.getWeight(), o.getWeight());
		return weight == 0 ? name : weight;
	}

	public void addSetting(String setting, Object value) {
		if (getSettings() == null) {
			this.setSettings(new HashMap<String, Object>());
		}
		this.getSettings().put(setting, value);
	}
}
