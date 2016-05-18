package com.pungwe.cms.modules.text.widget;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.field.widgets.TextareaWidget;
import com.pungwe.cms.core.form.element.TextareaElement;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/01/2016.
 */
@FieldWidget(value="textarea_with_summary_widget", label="Textarea with Summary", supports = "text_field")
public class TextareaWithSummaryWidget implements FieldWidgetDefinition<Map<String, String>> {

    @Override
    public Map<String, Object> getDefaultSettings() {
        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("rows", "10");
        settings.put("summary_rows", "5");
        return settings;
    }

    @Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, Map<String, String> value, int delta) {
        Map<String, Object> settings = field.getSettings();
        if (settings == null) {
            settings = getDefaultSettings();
        }
        // Get the default value
        Object defaultValue = settings.get("value");
        String defaultSummaryValue = "";
        String defaultTextValue = "";
        if (defaultValue != null && Map.class.isAssignableFrom(defaultValue.getClass())) {
            defaultSummaryValue = ((Map<String, String>)defaultValue).getOrDefault("summary", "");
            defaultTextValue = ((Map<String, String>)defaultValue).getOrDefault("text", "");
        } else if (defaultValue != null && List.class.isAssignableFrom(defaultValue.getClass())
                && ((List)defaultValue).size() > delta) {
            Object listValue = ((List<Object>)defaultValue).get(delta);
            if (listValue != null && Map.class.isAssignableFrom(listValue.getClass())) {
                defaultSummaryValue = ((Map<String, String>)listValue).get("summary");
                defaultTextValue = ((Map<String, String>)listValue).get("text");
            } else if (String.class.isAssignableFrom(listValue.getClass())) {
                defaultSummaryValue = "";
                defaultTextValue = (String)listValue;
            }
        }

        if (value != null && value.containsKey("summary")) {
            defaultSummaryValue = value.get("summary");
        }

        if (value != null && value.containsKey("value")) {
            defaultTextValue = value.get("value");
        }

        // FIXME: Switch for show summary
        // Text Field is a type of String element
        TextareaElement summary = new TextareaElement();
        summary.setLabel(field.getLabel() + " Summary");
        summary.setName(field.getName() + "_summary");
        summary.setRequired(field.isRequired());
        summary.setDefaultValue(defaultSummaryValue);
        summary.setRows(Integer.parseInt((String)settings.getOrDefault("summary_rows", 10)));
        summary.setSize(1024);

        elements.add(summary);

		TextareaElement element = new TextareaElement();
		element.setLabel(field.getLabel());
		element.setName(field.getName() + "_text");
		element.setRequired(field.isRequired());
		element.setDefaultValue(defaultTextValue);
		element.setRows(Integer.parseInt((String)settings.getOrDefault("rows", 10)));
		element.setSize(-1); // unlimted

		elements.add(element);
	}

    @Override
    public Map<String, String> extractValueFromForm(FieldConfig field, Map<String, Object> values, int delta) {
        if (!values.containsKey(field.getName() + "_summary") && !values.containsKey(field.getName() + "_text")) {
            return null;
        }

        Map<String, String> extractedValues = new LinkedHashMap<>();
        String summary = extractValue(field.getName() + "_summary", values, delta);
        String text = extractValue(field.getName() + "_text", values, delta);
        if (StringUtils.isNotBlank(summary)) {
            extractedValues.put("summary", summary);
        }
        if (StringUtils.isNotBlank(text)) {
            extractedValues.put("text", text);
        }
        return extractedValues;
    }

    private String extractValue(String name, Map<String, Object> values, int delta) {
        Object value = values.get(name);
        if (value == null) {
            return null;
        }
        if (List.class.isAssignableFrom(value.getClass()) && ((List<String>)value).size() > delta) {
            return ((List<String>)value).get(delta);
        } else if (String.class.isAssignableFrom(value.getClass())) {
            return (String)value;
        }
        return "";
    }

    @Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

	}
}
