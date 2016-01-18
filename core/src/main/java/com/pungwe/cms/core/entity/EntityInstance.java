package com.pungwe.cms.core.entity;

import java.util.Date;
import java.util.Map;

/**
 * Created by ian on 01/12/2015.
 */
public interface EntityInstance<ID extends EntityInstanceId> {

	/**
	 * The id of entity instance is made up of a UUID and and entity type information.
	 * This guarantees the ability to find instances of a particular type very efficiently as it's stored
	 * in the primary index as a compound key.
	 * <p>
	 * Entity instances can be retrieved as a list by type information (both partially by type, bundle or by it's UUID)
	 *
	 * @return The id of the entity made of UUID,EntityTypeInfo
	 */
	ID getId();

	void setId(ID id);

	/**
	 * Date the entity was created
	 *
	 * @return the creation date of the entity
	 */
	Date getDateCreated();

	/**
	 * Sets the creation date of the entity
	 *
	 * @param date the creation date for the entity
	 */
	void setDateCreated(Date date);

	/**
	 * The date the entity was last modified
	 *
	 * @return the date the entity was modified
	 */
	Date getDateModified();

	/**
	 * Sets the date the entity was modified
	 *
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
	 *
	 * @return
	 */
	Map<String, ?> getData();

	/**
	 * Sets the data value of the entity
	 *
	 * @param data the entity data
	 */
	void setData(Map<String, ?> data);
}
