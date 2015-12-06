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
public class EntityInstanceIdImpl implements EntityInstanceId<EntityTypeInfoImpl> {
    private UUID id;
    private EntityTypeInfoImpl entityType;

    @Override
    @Column(name="entity_instance_id", columnDefinition = "CHAR(32)")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
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
