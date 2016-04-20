package com.pungwe.cms.core.field;

import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by ian on 08/01/2016.
 */
public interface FieldFormatterDefinition<T> {

    Map<String, Object> getDefaultSettings();
    void format(List<RenderedElement> elements, List<T> value, FieldConfig config);
    void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings);

}
