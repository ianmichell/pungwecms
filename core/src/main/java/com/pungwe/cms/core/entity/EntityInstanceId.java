package com.pungwe.cms.core.entity;

import java.io.Serializable;

/**
 * Created by ian on 05/12/2015.
 */
public interface EntityInstanceId<ID extends Serializable, ET extends EntityTypeInfo> extends Serializable {

	ID getId();

	void setId(ID id);

	ET getEntityType();

	void setEntityType(ET entityType);

}
