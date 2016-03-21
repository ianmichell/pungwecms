package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.StringRenderedElement;
import com.pungwe.cms.core.form.element.TextareaRenderedElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 08/01/2016.
 */
@FieldWidget(value = "textarea_widget", label = "Text Area", supports = "string")
public class TextareaWidget implements FieldWidgetDefinition {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate) {

		// Text Field is a type of String element
		TextareaRenderedElement element = new TextareaRenderedElement();
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

		StringRenderedElement rows = new StringRenderedElement();
		rows.setName("rows");
		rows.setLabel("Rows");
		rows.setDefaultValue(5 + "");
		rows.setRequired(true);
		rows.setWeight(6);

		elements.add(rows);
	}

}
