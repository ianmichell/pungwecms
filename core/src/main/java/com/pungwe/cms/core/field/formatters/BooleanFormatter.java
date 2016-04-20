package com.pungwe.cms.core.field.formatters;

import com.pungwe.cms.core.annotations.stereotypes.FieldFormatter;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldFormatterDefinition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ian on 10/01/2016.
 */
@FieldFormatter(
        value = "boolean_formatter",
        label = "Boolean Formatter",
        supports = "boolean_field"
)
public class BooleanFormatter implements FieldFormatterDefinition<Boolean> {

    @Override
    public Map<String, Object> getDefaultSettings() {
        return new LinkedHashMap<>();
    }

    @Override
    public void format(List<RenderedElement> elements, List<Boolean> value, FieldConfig config) {

    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

    }
}
