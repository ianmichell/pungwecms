package com.pungwe.cms.modules.text.widget;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.widgets.TextareaWidget;
import com.pungwe.cms.core.form.Form;
import com.pungwe.cms.core.form.FormState;
import com.pungwe.cms.core.form.element.TextareaElement;

import java.util.List;

/**
 * Created by ian on 09/01/2016.
 */
public class TextareaWithSummaryWidget extends TextareaWidget {

    @Override
    public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, int delta, Form form, FormState sate) {

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
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Form form, FormState state) {
        super.buildWidgetSettingsForm(elements, form, state);
    }
}
