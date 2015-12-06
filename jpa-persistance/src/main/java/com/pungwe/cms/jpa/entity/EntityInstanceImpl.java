package com.pungwe.cms.jpa.entity;

import com.pungwe.cms.core.entity.EntityInstance;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Map;

/**
 * Created by ian on 05/12/2015.
 */
@Entity
@Table(name = "entity_instance")
public class EntityInstanceImpl implements EntityInstance<EntityInstanceIdImpl> {

    @Override
    @EmbeddedId
    public EntityInstanceIdImpl getId() {
        return null;
    }

    @Override
    public void setEntityId(EntityInstanceIdImpl id) {

    }

    @Override
    public Date getDateCreated() {
        return null;
    }

    @Override
    public void setDateCreated(Date date) {

    }

    @Override
    public Date getDateModified() {
        return null;
    }

    @Override
    public void setDateModified(Date date) {

    }

    @Override
    public Map<String, ?> getAttributes() {
        return null;
    }

    @Override
    public void setAttributes(Map<String, ?> attribute) {

    }

    @Override
    public Map<String, ?> getData() {
        return null;
    }

    @Override
    public void setData(Map<String, ?> data) {

    }
}
