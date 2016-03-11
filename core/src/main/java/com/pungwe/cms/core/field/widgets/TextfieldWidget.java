package com.pungwe.cms.core.field.widgets;


import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.StringElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 08/01/2016.
 */
@FieldWidget(value = "textfield_widget", label="Textfield", supports = "string")
public class TextfieldWidget implements FieldWidgetDefinition {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate) {

		// Text Field is a type of String element
		StringElement element = new StringElement();
		element.setLabel(field.getLabel());
		element.setName("value");
		element.setRequired(field.isRequired());
		element.setDefaultValue((String) ((List<String>) field.getSettings().getOrDefault("defaultValue", "")).get(delta));
		element.setSize((int) field.getSettings().getOrDefault("size", 60));

		elements.add(element);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state, Map<String, Object> settings) {

		StringElement size = new StringElement();
		size.setName("size");
		size.setLabel("Size");
		size.setDefaultValue(60 + "");
		size.setWeight(4);

		// Add Size to element list
		elements.add(size);

		// Text Field is a type of String element
		StringElement defaultValue = new StringElement();
		defaultValue.setLabel("Default Value");
		defaultValue.setName("defaultValue");
		defaultValue.setDefaultValue("");
		defaultValue.setSize(60);
		defaultValue.setWeight(5);

		// Add Default Value to element list
		elements.add(defaultValue);

	}
}
