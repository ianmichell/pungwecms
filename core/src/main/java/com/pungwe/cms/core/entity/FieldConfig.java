package com.pungwe.cms.core.entity;

import java.util.Map;

/**
 * Created by ian on 01/12/2015.
 */
public interface FieldConfig {

    int getWeight();

    void setWeight(int weight);

    String getName();

    void setName(String name);

    Map<String, ?> getSettings();

    void setSettings(Map<String, ?> settings);

    Class<FieldType> getFieldType();

    void setFieldType(Class<FieldType> type);

    Class<FieldStorageType> getStorageType();
    void setStorageType();
}
