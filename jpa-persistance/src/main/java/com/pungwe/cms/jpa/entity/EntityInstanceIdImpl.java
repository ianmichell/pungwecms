package com.pungwe.cms.jpa.entity;

import com.pungwe.cms.core.entity.EntityInstanceId;
import com.pungwe.cms.core.entity.EntityTypeInfo;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by ian on 05/12/2015.
 */
@Embeddable
public class EntityInstanceIdImpl implements EntityInstanceId<String, EntityTypeInfoImpl> {
    private String id;
    private EntityTypeInfoImpl entityType;

    public EntityInstanceIdImpl(String id, EntityTypeInfoImpl entityType) {
        this.id = id;
        this.entityType = entityType;
    }

    public EntityInstanceIdImpl() {
    }

    @Override
    @Column(name="entity_instance_id")
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @Embedded
    public EntityTypeInfoImpl getEntityType() {
        return entityType;
    }

    @Override
    public void setEntityType(EntityTypeInfoImpl entityType) {
        this.entityType = entityType;
    }


}
