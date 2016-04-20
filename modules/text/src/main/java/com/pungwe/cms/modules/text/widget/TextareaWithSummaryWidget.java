package com.pungwe.cms.modules.text.widget;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.widgets.TextareaWidget;
import com.pungwe.cms.core.form.element.TextareaElement;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
@FieldWidget(value="textarea_with_summary_widget", label="Textarea with Summary", supports = "text_field")
public class TextareaWithSummaryWidget extends TextareaWidget {

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, String value, int delta) {

		TextareaElement element = new TextareaElement();
		element.setLabel(field.getLabel());
		element.setName("value");
		element.setRequired(field.isRequired());
		element.setDefaultValue((String) ((List<String>) field.getSettings().getOrDefault("defaultValue", "")).get(delta));
		element.setRows((int) field.getSettings().getOrDefault("summary_rows", 10));
		element.setSize(-1); // unlimted

		elements.add(element);

		// FIXME: Switch for show summary
		// Text Field is a type of String element
		TextareaElement summary = new TextareaElement();
		summary.setLabel(field.getLabel() + " Summary");
		summary.setName("summary");
		summary.setRequired(field.isRequired());
		summary.setDefaultValue("");
		summary.setRows((int) field.getSettings().getOrDefault("rows", 10));
		summary.setSize(1024); // unlimted

		elements.add(summary);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {
		super.buildWidgetSettingsForm(elements, settings);
	}
}
