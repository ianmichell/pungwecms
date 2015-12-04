package com.pungwe.cms.core.entity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 01/12/2015.
 */
public interface EntityDefinition<T extends EntityDefinition> {

    /**
     * Primary identifier of the entity
     * @return the ID UUID of the entity
     */
    UUID getId();

    /**
     * Sets the UUID of the entity
     * @param id The UUID to be used
     */
    void setId(UUID id);

    /**
     * Date the entity was created
     * @return the creation date of the entity
     */
    Date getDateCreated();

    /**
     * Sets the creation date of the entity
     * @param date the creation date for the entity
     */
    void setDateCreated(Date date);

    /**
     * The date the entity was last modified
     * @return the date the entity was modified
     */
    Date getDateModified();

    /**
     * Sets the date the entity was modified
     * @param date
     */
    void setDateModified(Date date);

    /**
     * The entity name
     * @return the name of the entity
     */
    String getName();

    /**
     * Sets the name of the entity
     * @param name of the entity
     */
    void setName(String name);

    /**
     * Fetches entity configuration or settings for this entity type
     *
     * @return the configuration for the entity
     */
    Map<String, ?> getConfig();

    /**
     * Sets entity configuration
     *
     * @param config Configuration for the entity
     */
    void setConfig(Map<String, ?> config);

    /**
     * Gets the field groups for this entity
     *
     * @return The field groups for this entity
     */
    SortedSet<? extends FieldGroupConfig> getFieldGroups();

    void addFieldGroup(FieldGroupConfig<? extends FieldGroupConfig>... fieldGroup);

    default void addFieldGroup(String group, FieldGroupConfig<? extends FieldGroupConfig>... fieldGroups) {
        Optional<? extends FieldGroupConfig> g = getFieldGroupByName(group);
        if (!g.isPresent()) {
            throw new IllegalArgumentException("Entity definition does have field group: " + group);
        }
        for (FieldGroupConfig fieldGroup : fieldGroups) {
            g.get().addChild(fieldGroup.getName());
            addFieldGroup(fieldGroup);
        }
    }

    /**
     * Returns the fields in this entity definition
     *
     * @return the fields for this entity definition
     */
    SortedSet<? extends FieldConfig> getFields();

    void addField(FieldConfig<? extends FieldConfig>... field);

    default void addField(String group, FieldConfig<? extends FieldConfig>... fields) {
        Optional<? extends FieldGroupConfig> g = getFieldGroupByName(group);
        if (!g.isPresent()) {
            throw new IllegalArgumentException("Entity definition does have field group: " + group);
        }
        for (FieldConfig field : fields) {
            g.get().addChild(field.getName());
            addField(field);
        }
    }

    default boolean hasField(String name) {
        return getFieldByName(name).isPresent();
    }

    default boolean hasFieldGroup(String name) {
        return getFieldGroupByName(name).isPresent();
    }

    default boolean hasChild(String name) {
        return hasField(name) || hasFieldGroup(name);
    }

    default SortedSet<? extends FieldConfig> getFieldsByGroup(String group) {
        if (getFields() == null || getFieldGroups() == null) {
            return new TreeSet<>();
        }
        // Get the field group
        Optional<? extends FieldGroupConfig> fieldGroup = getFieldGroupByName(group);
        // If the field group is not there, then return an empty optional
        if (!fieldGroup.isPresent()) {
            return new TreeSet<>();
        }

        // Fetch the fields with the appropriate name
        return getFields().parallelStream().filter(f -> fieldGroup.get().getChildren().contains(f.getName())).collect(Collectors.toCollection(TreeSet::new));
    }

    default SortedSet<? extends FieldGroupConfig> getFieldGroupsByParent(String group) {
        Optional<? extends FieldGroupConfig> fg = getFieldGroupByName(group);
        if (!fg.isPresent()) {
            return new TreeSet<>();
        }
        return getFieldGroups().parallelStream().filter(g -> fg.get().getChildren().contains(g.getName())).collect(Collectors.toCollection(TreeSet::new));
    }

    default Optional<? extends FieldGroupConfig> getFieldGroupByName(String name) {
        return getFieldGroups() != null ? getFieldGroups().parallelStream().filter(g -> g.getName().equals(name)).findFirst() : Optional.empty();
    }

    default Optional<? extends FieldConfig> getFieldByName(String name) {
        return getFields() != null ? getFields().parallelStream().filter(f -> f.getName().equals(name)).findFirst() : Optional.empty();
    }

}
