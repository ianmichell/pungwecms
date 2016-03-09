package com.pungwe.cms.core.block;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 05/03/2016.
 */
public interface BlockDefinition {
	Map<String, Object> getDefaultSettings();
	void build(List<RenderedElement> elements, Map<String, Object> settings, final Map<String, Object> variables);
	void buildSettingsForm(List<RenderedElement> elements, Form form, FormState state);
}
