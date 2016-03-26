package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 10/01/2016.
 */
@FieldWidget(value = "email_widget", label = "Email Address", supports = "email_field")
public class EmailWidget implements FieldWidgetDefinition {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate) {

	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state, Map<String, Object> settings) {

	}

}