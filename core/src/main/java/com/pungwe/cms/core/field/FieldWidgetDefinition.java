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

	void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, T value, int delta);

	void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings);

}
