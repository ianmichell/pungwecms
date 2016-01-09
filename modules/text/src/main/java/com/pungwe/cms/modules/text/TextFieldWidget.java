package com.pungwe.cms.modules.text;


import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldType;
import com.pungwe.cms.core.field.FieldWidget;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.IntegerElement;
import com.pungwe.cms.core.form.element.StringElement;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * Created by ian on 08/01/2016.
 */
public class TextFieldWidget implements FieldWidget {

    @Override
    public void buildWidgetForm(List<RenderedElement> elements, FieldConfig<?> field, FieldType fieldType, int delta, Form form, FormState sate) {

        // Text Field is a type of String element
        StringElement element = new StringElement();
        element.setLabel(field.getLabel());
        element.setName(field.getName());
        element.setDetla(delta);
        element.setRequired(field.isRequired());
        element.setDefaultValue((String) field.getSettings().getOrDefault("defaultValue", ""));
        element.setSize((int)field.getSettings().getOrDefault("size", 60));

        elements.add(element);
    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state) {

        IntegerElement size = new IntegerElement();
        size.setName("size");
        size.setLabel("Size");
        size.setDefaultValue(60);
        size.setWeight(4);

        // Add Size to element list
        elements.add(size);

        // Text Field is a type of String element
        StringElement defaultValue = new StringElement();
        defaultValue.setLabel("Default Value");
        defaultValue.setName("defaultValue");
        defaultValue.setDefaultValue("");
        defaultValue.setSize(60);
        defaultValue.setWeight(5);

        // Add Default Value to element list
        elements.add(defaultValue);

    }


    @Override
    public boolean supports(Class<? extends FieldType> fieldType) {
        return TextField.class.isAssignableFrom(fieldType);
    }
}
