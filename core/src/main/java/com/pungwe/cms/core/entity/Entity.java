package com.pungwe.cms.core.entity;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ian on 01/12/2015.
 */
public interface Entity {

    /**
     * Primary identifier of the entity
     * @return the ID UUID of the entity
     */
    UUID getId();

    /**
     * Sets the UUID of the entity
     * @param id The UUID to be used
     */
    void setId(UUID id);

    /**
     * Date the entity was created
     * @return the creation date of the entity
     */
    Date getDateCreated();

    /**
     * Sets the creation date of the entity
     * @param date the creation date for the entity
     */
    void setDateCreated(Date date);

    /**
     * The date the entity was last modified
     * @return the date the entity was modified
     */
    Date getDateModified();

    /**
     * Sets the date the entity was modified
     * @param date
     */
    void setDateModified(Date date);

    /**
     * The entity name
     * @return the name of the entity
     */
    String getName();

    /**
     * Sets the name of the entity
     * @param name of the entity
     */
    void setName(String name);

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
}
