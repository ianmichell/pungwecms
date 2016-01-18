package com.pungwe.cms.core.entity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ian on 01/12/2015.
 */
public interface EntityDefinition<ET extends EntityTypeInfo> {

	ET getId();

	void setId(ET type);

	String getTitle();

	void setTitle(String title);

	String getDescription();

	void setDescription(String description);

	/**
	 * Date the entity was created
	 *
	 * @return the creation date of the entity
	 */
	Date getDateCreated();

	/**
	 * Sets the creation date of the entity
	 *
	 * @param date the creation date for the entity
	 */
	void setDateCreated(Date date);

	/**
	 * The date the entity was last modified
	 *
	 * @return the date the entity was modified
	 */
	Date getDateModified();

	/**
	 * Sets the date the entity was modified
	 *
	 * @param date
	 */
	void setDateModified(Date date);

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
	SortedSet<FieldGroupConfig> getFieldGroups();

	void addFieldGroup(FieldGroupConfig... fieldGroup);

	default void addFieldGroup(String group, FieldGroupConfig... fieldGroups) {
		Optional<FieldGroupConfig> g = getFieldGroupByName(group);
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
	SortedSet<FieldConfig> getFields();

	void addField(FieldConfig... field);

	default void addField(String group, FieldConfig... fields) {
		Optional<FieldGroupConfig> g = getFieldGroupByName(group);
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

	default SortedSet<FieldConfig> getFieldsByGroup(String group) {
		if (getFields() == null || getFieldGroups() == null) {
			return new TreeSet<>();
		}
		// Get the field group
		Optional<FieldGroupConfig> fieldGroup = getFieldGroupByName(group);
		// If the field group is not there, then return an empty optional
		if (!fieldGroup.isPresent()) {
			return new TreeSet<>();
		}

		// Fetch the fields with the appropriate name
		return getFields().parallelStream().filter(f -> fieldGroup.get().getChildren().contains(f.getName())).collect(Collectors.toCollection(TreeSet::new));
	}

	default SortedSet<FieldGroupConfig> getFieldGroupsByParent(String group) {
		Optional<FieldGroupConfig> fg = getFieldGroupByName(group);
		if (!fg.isPresent()) {
			return new TreeSet<>();
		}
		return getFieldGroups().parallelStream().filter(g -> fg.get().getChildren().contains(g.getName())).collect(Collectors.toCollection(TreeSet::new));
	}

	default Optional<FieldGroupConfig> getFieldGroupByName(String name) {
		return getFieldGroups() != null ? getFieldGroups().parallelStream().filter(g -> g.getName().equals(name)).findFirst() : Optional.empty();
	}

	default Optional<FieldConfig> getFieldByName(String name) {
		return getFields() != null ? getFields().parallelStream().filter(f -> f.getName().equals(name)).findFirst() : Optional.empty();
	}

}
