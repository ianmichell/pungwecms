package com.pungwe.cms.core.entity;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ian on 01/12/2015.
 */
public interface EntityInstance {

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
     * Fetches entity attributes. This acts as storage for non-field related information
     *
     * @return
     */
    Map<String, ?> getAttributes();

    /**
     * Sets entity attributes. This acts as storage for non-field related information
     *
     * @param attribute
     */
    void setAttributes(Map<String, ?> attribute);

    /**
     * Map containing the entity data
     * @return
     */
    Map<String, ?> getData();

    /**
     * Sets the data value of the entity
     * @param data the entity data
     */
    void setData(Map<String, ?> data);
}
