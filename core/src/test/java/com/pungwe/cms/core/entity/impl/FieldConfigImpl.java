package com.pungwe.cms.core.entity.impl;

import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.FieldStorageType;
import com.pungwe.cms.core.entity.FieldType;

import java.util.Map;

/**
 * Created by ian on 03/12/2015.
 */
public class FieldConfigImpl implements FieldConfig<FieldConfigImpl> {

    private int weight;
    private String name;
    private Map<String, ?> settings;
    private Class<? extends FieldType> fieldType;
    private Class<? extends FieldStorageType> fieldStorageType;

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
    public Class<? extends FieldType> getFieldType() {
        return fieldType;
    }

    @Override
    public void setFieldType(Class<? extends FieldType> fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public Class<? extends FieldStorageType> getStorageType() {
        return fieldStorageType;
    }

    @Override
    public void setStorageType(Class<? extends FieldStorageType> fieldStorageType) {
        this.fieldStorageType = fieldStorageType;
    }
}
