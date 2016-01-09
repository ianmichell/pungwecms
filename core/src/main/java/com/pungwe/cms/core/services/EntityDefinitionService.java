package com.pungwe.cms.core.services;

import com.pungwe.cms.core.entity.EntityDefinition;
import com.pungwe.cms.core.entity.EntityType;
import org.springframework.stereotype.Service;

/**
 * Created by ian on 09/01/2016.
 */
public interface EntityDefinitionService<ED extends EntityDefinition> {

    /**
     * Instantiates the EntityDefinition class
     * @param type The type of entity
     * @param bundle The entity bundle
     * @return
     */
    ED newInstance(EntityType type, String bundle);

    ED get(String type, String bundle);

    void save(ED instance);

    void remove(String type, String bundle);

}
