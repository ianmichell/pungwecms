package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidget;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.StringElement;
import com.pungwe.cms.core.form.element.TextareaElement;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 08/01/2016.
 */
public class TextareaWidget implements FieldWidget {

	@Override
	public String getName() {
		return "textarea";
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate) {

		// Text Field is a type of String element
		TextareaElement element = new TextareaElement();
		element.setLabel(field.getLabel());
		element.setName("value");
		element.setRequired(field.isRequired());
		element.setDefaultValue((String) ((List<String>) field.getSettings().getOrDefault("defaultValue", "")).get(delta));
		element.setRows((int) field.getSettings().getOrDefault("rows", 10));
		element.setSize(-1); // unlimted

		elements.add(element);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state, Map<String, Object> settings) {

		StringElement rows = new StringElement();
		rows.setName("rows");
		rows.setLabel("Rows");
		rows.setDefaultValue(5 + "");
		rows.setRequired(true);
		rows.setWeight(6);

		elements.add(rows);
	}

	@Override
	public boolean supports(String fieldType) {
		return "string".equals(fieldType);
	}

}
