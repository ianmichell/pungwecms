package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

	}

}