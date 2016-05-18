package com.pungwe.cms.core.entity;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ian on 03/12/2015.
 */
final public class FieldGroupConfig implements Comparable<FieldGroupConfig> {

	private int weight;
	private String name;
	private String label;
	private Map<String, ?> settings;
	private String fieldGroupType;
	private Set<String> children;


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


	public Map<String, ?> getSettings() {
		return settings;
	}


	public void setSettings(Map<String, ?> settings) {
		this.settings = settings;
	}


	public String getFieldGroupType() {
		return fieldGroupType;
	}


	public void setFieldGroupType(String fieldGroupType) {
		this.fieldGroupType = fieldGroupType;
	}


	public Set<String> getChildren() {
		if (children == null) {
			children = new TreeSet<>();
		}
		return children;
	}


	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FieldGroupConfig that = (FieldGroupConfig) o;

		return name.equals(that.name);

	}

	public int hashCode() {
		return name.hashCode();
	}

	public void addChild(String... children) {
		if (getChildren() != null) {
			for (String child : children) {
				getChildren().add(child);
			}
		}
	}

	public boolean hasChild(String child) {
		return getChildren() != null && getChildren().contains(child);
	}


	public int compareTo(FieldGroupConfig o) {
		int name = this.getName().compareTo(o.getName());
		if (name == 0) {
			return name;
		}
		int weight = Integer.compare(this.getWeight(), o.getWeight());
		return weight == 0 ? name : weight;
	}

	public String getLabel() {
		return label;
	}

    public void setLabel(String label) {
        this.label = label;
    }
}
