package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.FormRenderedElement;
import com.pungwe.cms.core.form.element.CheckboxElement;
import com.pungwe.cms.core.form.element.RadioElement;
import com.pungwe.cms.core.form.element.SingleSelectListElement;
import com.pungwe.cms.core.form.element.StringElement;

import java.util.HashMap;
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
        String type = (String)field.getSettings().getOrDefault("type", "checkbox");
        String labelTrue = (String)field.getSettings().getOrDefault("true_label", translate("True"));
        String labelFalse = (String)field.getSettings().getOrDefault("false_label", translate("False"));
        boolean defaultValue = value != null ? value : (Boolean)field.getSettings().getOrDefault("checked_by_default", false);
        FormRenderedElement element = null;
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
        element.setDefaultValue("true");
        element.setName(field.getName());

        if (element instanceof SingleSelectListElement) {
            SingleSelectListElement select = (SingleSelectListElement)element;
            select.addOption(labelTrue, "true");
            select.addOption(labelFalse, "false");
        } else {
            CheckboxElement checkbox = (CheckboxElement)element;
            checkbox.setLabel(field.getLabel());
            checkbox.setChecked(defaultValue);
        }
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

        CheckboxElement checkboxElement = new CheckboxElement();
        checkboxElement.setLabel(translate("True by default"));
        checkboxElement.setDefaultValue("false");
        checkboxElement.setName("checked_by_default");
        checkboxElement.setChecked((Boolean)settings.getOrDefault("checked_by_default", false));
        elements.add(checkboxElement);

        StringElement trueLabel = new StringElement();
        trueLabel.setLabel(translate("Label for \"True\""));
        trueLabel.setName("true_label");
        trueLabel.setDefaultValue((String)settings.getOrDefault("true_label", translate("True")));
        elements.add(trueLabel);

        StringElement falseLabel = new StringElement();
        falseLabel.setLabel(translate("Label for \"False\""));
        falseLabel.setName("false_label");
        falseLabel.setDefaultValue((String)settings.getOrDefault("false_label", translate("False")));
        elements.add(trueLabel);
	}

}
