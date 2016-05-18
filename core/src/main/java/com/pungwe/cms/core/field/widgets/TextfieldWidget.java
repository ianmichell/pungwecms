package com.pungwe.cms.core.field.widgets;


import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.form.validation.NumberValidator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 08/01/2016.
 */
@FieldWidget(value = "textfield_widget", label = "Textfield", supports = "string_field")
public class TextfieldWidget implements FieldWidgetDefinition<String> {

    @Override
    public Map<String, Object> getDefaultSettings() {
        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("field_size", "60");
        return settings;
    }

    @Override
    public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, String value, int delta) {

        // Get the default value
        Object defaultValue = field.getSettings().get("value");
        if (defaultValue != null && List.class.isAssignableFrom(defaultValue.getClass())
                && ((List<String>) defaultValue).size() > delta) {
            defaultValue = ((List<String>) defaultValue).get(delta);
        } else if (defaultValue == null) {
            defaultValue = "";
        }

        // Text Field is a type of String element
        TextElement element = new TextElement();
        element.setLabel(translate(field.getLabel()));
        element.setName(field.getName());
        element.setRequired(field.isRequired());
        element.setDefaultValue((String)defaultValue);
        element.setValue(value);
        element.setDelta(delta);
        element.setSize(Integer.parseInt((String) field.getSettings().getOrDefault("field_size", "60")));

        elements.add(element);
    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

        TextElement size = new TextElement();
        size.setName("field_size");
        size.setLabel(translate("Size"));
        size.setDefaultValue((String) settings.get("field_size"));
        size.setWeight(4);

        size.addValidator(new NumberValidator());

        // Add Size to element list
        elements.add(size);

    }

    @Override
    public String extractValueFromForm(FieldConfig field, Map<String, Object> values, int delta) {
        Object value = values.get(field.getName());
        if (value == null) {
            return null;
        }
        if (List.class.isAssignableFrom(value.getClass()) && ((List<String>)value).size() > delta) {
            return ((List<String>)value).get(delta);
        }
        return (String)value;
    }
}
