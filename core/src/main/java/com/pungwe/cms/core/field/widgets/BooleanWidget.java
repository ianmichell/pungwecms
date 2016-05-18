package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.CheckboxElement;
import com.pungwe.cms.core.form.element.RadioElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.TextElement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 10/01/2016.
 */
@FieldWidget(value = "boolean_widget", label = "Boolean", supports = "boolean_field")
public class BooleanWidget implements FieldWidgetDefinition<Boolean> {

	@Override
	public Map<String, Object> getDefaultSettings() {
		Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("type", "checkbox");
        return settings;
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, final Boolean value, int delta) {
        Map<String, Object> widgetSettings = (Map<String, Object>)field.getSettings();
        if (widgetSettings == null) {
            widgetSettings = getDefaultSettings();
        }
        String type = (String)widgetSettings.getOrDefault("type", "checkbox");
        if (type == null) {
            type = "checkbox";
        }
        String labelTrue = (String)widgetSettings.getOrDefault("true_label", translate("True"));
        String labelFalse = (String)widgetSettings.getOrDefault("false_label", translate("False"));
        String defaultValue = value != null ? String.valueOf(value) : (String)widgetSettings.get("value");
        FormRenderedElement<String> element = null;
        switch (type) {
            case "radio":
                element = new RadioElement();
                break;
            case "select":
                element = new SingleSelectListElement();
                break;
            default:
                element = new CheckboxElement();
                break;
        }
        // Setup defaults
        element.setDelta(delta);
        element.setName(field.getName());

        if (element instanceof SingleSelectListElement) {
            SingleSelectListElement select = (SingleSelectListElement)element;
            select.addOption(labelTrue, "true");
            select.addOption(labelFalse, "false");
            select.setDefaultValue(defaultValue);
        } else {
            CheckboxElement checkbox = (CheckboxElement)element;
            checkbox.addContent(labelTrue);
            checkbox.setValue(defaultValue);
            checkbox.setDefaultValue("true");
            checkbox.setChecked((String)defaultValue != null && ((String)defaultValue).equals("true"));
        }

        elements.add(element);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

        SingleSelectListElement control = new SingleSelectListElement();
        control.setName("type");
        control.addOption(translate("Checkbox"), "checkbox");
        control.addOption(translate("Radio Buttons"), "radio");
        control.addOption(translate("Select"), "select");
        control.setDefaultValue((String)settings.getOrDefault("type", "checkbox"));
        control.setLabel(translate("Control"));
        elements.add(control);

        TextElement trueLabel = new TextElement();
        trueLabel.setLabel(translate("Label for \"True\""));
        trueLabel.setName("true_label");
        trueLabel.setDefaultValue((String)settings.getOrDefault("true_label", translate("True")));
        elements.add(trueLabel);

        TextElement falseLabel = new TextElement();
        falseLabel.setLabel(translate("Label for \"False\""));
        falseLabel.setName("false_label");
        falseLabel.setDefaultValue((String)settings.getOrDefault("false_label", translate("False")));
        elements.add(falseLabel);
	}

    @Override
    public Boolean extractValueFromForm(FieldConfig field, Map<String, Object> values, int delta) {
        return null;
    }
}
