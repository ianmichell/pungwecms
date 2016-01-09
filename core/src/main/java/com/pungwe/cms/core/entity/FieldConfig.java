package com.pungwe.cms.core.entity;

import java.util.Map;

/**
 * Created by ian on 01/12/2015.
 */
public interface FieldConfig<T extends FieldConfig> extends Comparable<T> {

    int getWeight();

    void setWeight(int weight);

    String getName();

    void setName(String name);

    String getLabel();
    void setLabel(String label);

    public int getCardinality();

    void setCardinality(int cardinality);

    Map<String, Object> getSettings();

    void setSettings(Map<String, Object> settings);

    String getFieldType();

    void setFieldType(String type);

    String getStorageType();

    void setStorageType(String storageType);

    String getWidget();
    void setWidget(String widget);

    String getFormatter();
    void setFormatter(String formatter);

    boolean isRequired();
    void setRequired(boolean required);

    /**
     * Compared by weight, if they are equal they are sorted by name.
     *
     * @param o
     * @return
     */
    @Override
    default int compareTo(T o) {
        int name = this.getName().compareTo(o.getName());
        if (name == 0) {
            return name;
        }
        int weight = Integer.compare(this.getWeight(), o.getWeight());
        return weight == 0 ? name : weight;
    }
}
