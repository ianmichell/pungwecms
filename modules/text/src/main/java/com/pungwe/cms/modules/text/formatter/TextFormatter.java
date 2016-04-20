package com.pungwe.cms.modules.text.formatter;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldFormatterDefinition;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 25/03/2016.
 */
public class TextFormatter implements FieldFormatterDefinition<String> {
    @Override
    public Map<String, Object> getDefaultSettings() {
        return null;
    }

    @Override
    public void format(List<RenderedElement> elements, List<String> value, FieldConfig config) {

    }

    @Override
    public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

    }
}
