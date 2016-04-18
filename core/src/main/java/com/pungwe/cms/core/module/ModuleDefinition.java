package com.pungwe.cms.core.module;

/**
 * Created by ian on 11/01/2016.
 */

import com.pungwe.cms.core.element.RenderedElement;

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
	 * @param settings the module settings
	 */
	void buildSettingsForm(List<RenderedElement> elements, Map<String, Object> settings);

}
