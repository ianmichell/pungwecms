package com.pungwe.cms.modules.text.widget;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldType;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.IntegerElement;
import com.pungwe.cms.core.form.element.TextAreaElement;

import java.util.List;

/**
 * Created by ian on 08/01/2016.
 */
public class TextAreaWidget extends TextFieldWidget {

    @Override
    public String getName() {
        return "textarea";
    }

    @Override
    public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, FieldType fieldType, int delta, Form form, FormState sate) {

        // Text Field is a type of String element
        TextAreaElement element = new TextAreaElement();
        element.setLabel(field.getLabel());
        element.setName(field.getName());
        element.setDetla(delta);
        element.setRequired(field.isRequired());
        element.setDefaultValue((String) field.getSettings().getOrDefault("defaultValue", ""));
        element.setRows((int) field.getSettings().getOrDefault("rows", 60));

        elements.add(element);
    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
        super.buildWidgetSettingsForm(elements, form, state);

        IntegerElement rows = new IntegerElement();
        rows.setName("rows");
        rows.setLabel("Rows");
        rows.setDefaultValue(5);
        rows.setRequired(true);
        rows.setWeight(6);

        elements.add(rows);
    }
}
