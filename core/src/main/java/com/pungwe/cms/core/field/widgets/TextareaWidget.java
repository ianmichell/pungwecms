package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.form.element.TextareaElement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 08/01/2016.
 */
@FieldWidget(value = "textarea_widget", label = "Text Area", supports = "string_field")
public class TextareaWidget implements FieldWidgetDefinition<String> {

	@Override
	public Map<String, Object> getDefaultSettings() {
		Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("rows", "10");
        return settings;
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, String value, int delta) {

		// Text Field is a type of String element
		TextareaElement element = new TextareaElement();
		element.setLabel(translate(field.getLabel()));
		element.setName("value");
		element.setRequired(field.isRequired());
		element.setDefaultValue((String) field.getSettings().get("default_value"));
		element.setRows(field.getSettings().get("rows") != null ? Integer
                .valueOf((String) field.getSettings().get("rows")) : 10);
		element.setSize(-1); // unlimted

		elements.add(element);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

		TextElement rows = new TextElement();
		rows.setName("rows");
		rows.setLabel(translate("Rows"));
		rows.setDefaultValue((String) settings.get("rows"));
		rows.setRequired(true);
		rows.setWeight(6);

		elements.add(rows);

        // Text Field is a type of String element
        TextElement defaultValue = new TextElement();
        defaultValue.setLabel(translate("Default Value"));
        defaultValue.setName("default_value");
        defaultValue.setDefaultValue("");
        defaultValue.setSize(60);
        defaultValue.setWeight(5);

        elements.add(defaultValue);
	}

}
