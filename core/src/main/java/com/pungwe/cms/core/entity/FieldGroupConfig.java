package com.pungwe.cms.core.entity;

import java.util.Map;
import java.util.Set;

/**
 * Created by ian on 03/12/2015.
 */
public interface FieldGroupConfig<T extends FieldGroupConfig> extends Comparable<T> {

    int getWeight();

    void setWeight(int weight);

    String getName();

    void setName(String name);

    Map<String, ?> getSettings();

    void setSettings(Map<String, ?> settings);

    String getFieldGroupType();

    void setFieldGroupType(String fieldGroupType);

    Set<String> getChildren();

    default void addChild(String... children) {
        if (getChildren() != null) {
            for (String child : children) {
                getChildren().add(child);
            }
        }
    }

    default boolean hasChild(String child) {
        return getChildren() != null && getChildren().contains(child);
    }

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
