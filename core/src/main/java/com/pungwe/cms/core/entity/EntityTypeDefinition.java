package com.pungwe.cms.core.entity;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;

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
	void buildSettingsForm(List<RenderedElement> elements);
}
