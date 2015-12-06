package com.pungwe.cms.core.entity;

import java.util.UUID;

/**
 * Created by ian on 05/12/2015.
 */
public interface EntityInstanceId<ET extends EntityTypeInfo> {

    UUID getId();
    void setId(UUID id);

    ET getEntityType();
    void setEntityType(ET entityType);

}
