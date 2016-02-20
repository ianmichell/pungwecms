package com.pungwe.cms.core.field.widgets;


import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidget;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.StringElement;

import java.util.List;

/**
 * Created by ian on 08/01/2016.
 */
public class TextfieldWidget implements FieldWidget {

	@Override
	public String getName() {
		return "textfield";
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
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state) {

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


	@Override
	public boolean supports(String type) {
		return "string".equals(type);
	}
}
