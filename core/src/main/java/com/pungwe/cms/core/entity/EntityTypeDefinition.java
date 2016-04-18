package com.pungwe.cms.core.entity;

import com.pungwe.cms.core.element.RenderedElement;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
public interface EntityTypeDefinition {


	/**
	 * Creates a list of base fields for the entity type. These are added automatically when a new
	 * entity is created...
	 *
	 * @return the base fields for the entity type
	 */
	List<FieldConfig> getBaseFields();

	/**
	 * Builds an entity settings form.
	 *
	 * @param elements the list of elements for the form
	 */
	void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings);
}
