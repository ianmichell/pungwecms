package com.pungwe.cms.core.entity.impl;

import com.pungwe.cms.core.entity.FieldGroupConfig;
import com.pungwe.cms.core.entity.FieldGroupType;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ian on 03/12/2015.
 */
public class FieldGroupConfigImpl implements FieldGroupConfig<FieldGroupConfigImpl> {

    private int weight;
    private String name;
    private Map<String, ?> settings;
    private Class<? extends FieldGroupType> fieldGroupType;
    private Set<String> children;

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
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
    public Map<String, ?> getSettings() {
        return settings;
    }

    @Override
    public void setSettings(Map<String, ?> settings) {
        this.settings = settings;
    }

    @Override
    public Class<? extends FieldGroupType> getFieldGroupType() {
        return fieldGroupType;
    }

    @Override
    public void setFieldGroupType(Class<? extends FieldGroupType> type) {
        this.fieldGroupType = fieldGroupType;
    }

    @Override
    public Set<String> getChildren() {
        if (children == null) {
            children = new TreeSet<>();
        }
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldGroupConfigImpl that = (FieldGroupConfigImpl) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
