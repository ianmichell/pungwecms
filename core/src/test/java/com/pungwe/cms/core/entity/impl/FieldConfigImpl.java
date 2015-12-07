package com.pungwe.cms.core.entity.impl;

import com.pungwe.cms.core.entity.FieldConfig;

import java.util.Map;

/**
 * Created by ian on 03/12/2015.
 */
public class FieldConfigImpl implements FieldConfig<FieldConfigImpl> {

    private int weight;
    private String name;
    private int cardinality;
    private Map<String, ?> settings;
    private String fieldType;
    private String storageType;

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
    public int getCardinality() {
        return cardinality;
    }

    @Override
    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
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
    public String getFieldType() {
        return fieldType;
    }

    @Override
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String getStorageType() {
        return storageType;
    }

    @Override
    public void setStorageType(String fieldStorageType) {
        this.storageType = fieldStorageType;
    }
}
