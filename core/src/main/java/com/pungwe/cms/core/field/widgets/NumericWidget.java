package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.form.validation.DecimalValidator;
import com.pungwe.cms.core.form.validation.NumberValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 10/01/2016.
 */
@FieldWidget(value = "numeric_widget", label = "Number", supports = "number_field")
public class NumericWidget implements FieldWidgetDefinition<Number> {

    @Override
    public Map<String, Object> getDefaultSettings() {
        return new HashMap<>();
    }

    @Override
    public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, Number value, int delta) {
        TextElement number = new TextElement();
        number.setDefaultValue((String) field.getSettings().get("default_value"));
        number.setValue(value == null ? null : value.toString());
        number.setName(field.getName());
        number.setLabel(field.getLabel());
        number.setDelta(delta);

        // Validator
        number.addValidator(new NumberValidator());

        elements.add(number);
    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

        TextElement defaultValue = new TextElement();
        defaultValue.setName("default_value");
        defaultValue.setLabel(translate("Default Value"));
        defaultValue.addValidator(new NumberValidator());
        defaultValue.setDefaultValue((String) settings.get("default_value"));

        elements.add(defaultValue);
    }

}