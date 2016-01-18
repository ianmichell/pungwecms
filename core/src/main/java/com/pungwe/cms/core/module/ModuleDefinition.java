package com.pungwe.cms.core.module;

/**
 * Created by ian on 11/01/2016.
 */

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;
import java.util.Map;

/**
 * Used on modules that have dynamic configuration (optional)
 */
public interface ModuleDefinition {

	/**
	 * Default module settings
	 *
	 * @return the default module settings
	 */
	Map<String, Object> getDefaultSettings();

	/**
	 * Builds a settings form for the admin ui.
	 *
	 * @param elements the form elements
	 * @param form     the settings form
	 * @param state    the form state
	 */
	void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state);

}
