package com.pungwe.cms.core.entity.services;

import com.pungwe.cms.core.annotations.stereotypes.EntityType;
import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.EntityTypeDefinition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ian on 09/01/2016.
 */
public interface EntityDefinitionService<ED extends EntityDefinition> {

	/**
	 * Instantiates the EntityDefinition class
	 *
	 * @param type   The type of entity
	 * @param bundle The entity bundle
	 * @return
	 */
	ED newInstance(EntityTypeDefinition type, String bundle);

	ED newInstance(EntityTypeDefinition type);

	ED get(String type, String bundle);

	Page<ED> list(String type, Pageable page);

	void create(ED instance);

	void remove(String type, String bundle);

	default String getEntityTypeName(EntityTypeDefinition type) {
        EntityType typeInfo = AnnotationUtils.findAnnotation(type.getClass(), EntityType.class);
        if (typeInfo == null) {
            throw new IllegalArgumentException("Entity type not annotated with @EntityType");
        }
        return typeInfo.type();
	}
}
