package com.pungwe.cms.core.entity.impl;

import com.pungwe.cms.core.entity.*;

import java.util.Date;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by ian on 03/12/2015.
 */
public class EntityDefinitionImpl implements EntityDefinition<EntityTypeInfoImpl> {

    protected EntityTypeInfoImpl id;
    protected Date dateCreated;
    protected Date dateModified;
    protected Map<String, ?> config;
    protected SortedSet<FieldGroupConfig> fieldGroups;
    protected SortedSet<FieldConfig> fields;

    @Override
    public EntityTypeInfoImpl getId() {
        return id;
    }

    @Override
    public void setId(EntityTypeInfoImpl id) {
        this.id = id;
    }

    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Date getDateModified() {
        return dateModified;
    }

    @Override
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public Map<String, ?> getConfig() {
        return config;
    }

    @Override
    public void setConfig(Map<String, ?> config) {
        this.config = config;
    }

    @Override
    public SortedSet<FieldGroupConfig> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public SortedSet<FieldConfig> getFields() {
        return fields;
    }

    @Override
    public void addFieldGroup(FieldGroupConfig... fieldGroup) {
        if (this.fieldGroups == null) {
            this.fieldGroups = new TreeSet<>();
        }
        for (FieldGroupConfig f : fieldGroup) {
            if (hasChild(f.getName())) {
                throw new IllegalArgumentException("Name already exists as either a field group or a field");
            }
            this.fieldGroups.add(f);
        }
    }

    @Override
    public void addField(FieldConfig... field) {
        if (this.fields == null) {
            this.fields = new TreeSet<>();
        }

        for (FieldConfig f : field) {
            if (hasChild(f.getName())) {
                throw new IllegalArgumentException("Name already exists as either a field group or a field");
            }
            this.fields.add(f);
        }
    }

}
