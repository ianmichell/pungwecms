package com.pungwe.cms.core.field;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 09/12/2015.
 */
public interface FieldWidgetDefinition<T> {

    Map<String, Object> getDefaultSettings();

    /**
     * Builds the widget form. This is used for both the default value when defining a field and the value
     * entity form itself.
     *
     * @param elements the list of elements to add the form components to.
     * @param field the field being added
     * @param value the value of the field (returned from a posted form)
     * @param delta the delta of the field - usually 0, but will be the index of a multi value field.
     */
    void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, T value, int delta);

    /**
     * Builds a widget settings form.
     *
     * @param elements the element list to add the form controls to.
     *
     * @param settings the current widget settings
     */
    void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings);

    /**
     * Extracts the field value from the form.
     *
     * @param field the field the value is being extracted for
     * @param values the values from the form by field
     * @param delta the delta for the field
     * @return the value extracted from the form.
     */
    T extractValueFromForm(FieldConfig field, Map<String, Object> values, int delta);
}
