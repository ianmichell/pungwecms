package com.pungwe.cms.core.entity.impl;

import com.pungwe.cms.core.entity.FieldConfig;

import java.util.Map;

/**
 * Created by ian on 03/12/2015.
 */
public class FieldConfigImpl implements FieldConfig<FieldConfigImpl> {

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
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
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
    public Map<String, Object> getSettings() {
        return settings;
    }

    @Override
    public void setSettings(Map<String, Object> settings) {
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

    @Override
    public String getFormatter() {
        return formatter;
    }

    @Override
    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @Override
    public String getWidget() {
        return widget;
    }

    @Override
    public void setWidget(String widget) {
        this.widget = widget;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }
}
