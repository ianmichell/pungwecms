package com.pungwe.cms.jpa.entity;

import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.entity.FieldGroupConfig;
import com.pungwe.cms.jpa.converter.HashMapBinaryJSONConverter;
import com.pungwe.cms.jpa.converter.TreeSetBinaryJSONConverter;
import com.pungwe.cms.jpa.converter.TreeSetFieldConfigBinaryJSONConverter;
import com.pungwe.cms.jpa.converter.TreeSetFieldGroupBinaryJSONConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ian on 05/12/2015.
 */
@Entity
@Table(name = "entity_definition")
public class EntityDefinitionImpl implements EntityDefinition<EntityTypeInfoImpl> {

	protected String title;
	protected String description;
	protected EntityTypeInfoImpl id;
	protected Date dateCreated;
	protected Date dateModified;
	protected Map<String, ?> config;
	protected SortedSet<FieldGroupConfig> fieldGroups;
	protected SortedSet<FieldConfig> fields;

	public EntityDefinitionImpl() {

	}

	public EntityDefinitionImpl(EntityTypeInfoImpl id) {
		this.id = id;
	}

	@Override
	@EmbeddedId
	public EntityTypeInfoImpl getId() {
		return id;
	}

	@Override
	public void setId(EntityTypeInfoImpl id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Date getDateCreated() {
		return dateCreated != null ? (Date)dateCreated.clone() : null;
	}

	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated != null ? (Date)dateCreated.clone() : null;
	}

	@Override
	public Date getDateModified() {
		return dateModified != null ? (Date)dateModified.clone() : null;
	}

	@Override
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified != null ? (Date)dateModified.clone() : null;
	}

	@Override
	@Column(name = "config")
	@Convert(converter = HashMapBinaryJSONConverter.class)
	public Map<String, ?> getConfig() {
		return config;
	}

	@Override
	public void setConfig(Map<String, ?> config) {
		this.config = config;
	}

	@Override
	@Column(name = "field_groups")
	@Convert(converter = TreeSetFieldGroupBinaryJSONConverter.class)
	public SortedSet<FieldGroupConfig> getFieldGroups() {
		if (fieldGroups == null) {
			fieldGroups = new TreeSet<>();
		}
		return fieldGroups;
	}

	public void setFieldGroups(SortedSet<FieldGroupConfig> fieldGroups) {
		this.fieldGroups = fieldGroups;
	}

	@Override
	@Column(name = "fields")
	@Convert(converter = TreeSetFieldConfigBinaryJSONConverter.class)
	public SortedSet<FieldConfig> getFields() {
		if (fields == null) {
			fields = new TreeSet<>();
		}
		return fields;
	}

	public void setFields(SortedSet<FieldConfig> fields) {
		this.fields = fields;
	}

	@Override
	public void addFieldGroup(FieldGroupConfig... fieldGroup) {
		for (FieldGroupConfig f : fieldGroup) {
			if (hasChild(f.getName())) {
				throw new IllegalArgumentException("Name already exists as either a field group or a field");
			}
			this.fieldGroups.add(f);
		}
	}

	@Override
	public void addField(FieldConfig... field) {
		for (FieldConfig f : field) {
			if (hasChild(f.getName())) {
				throw new IllegalArgumentException("Name already exists as either a field group or a field");
			}
			this.fields.add(f);
		}
	}
}
