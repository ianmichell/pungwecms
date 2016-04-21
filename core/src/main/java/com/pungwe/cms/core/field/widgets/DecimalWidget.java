package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.element.TextElement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 10/01/2016.
 */
@FieldWidget(value = "decimal_widget", label = "Decimal", supports = "decimal_field")
public class DecimalWidget implements FieldWidgetDefinition<Double> {

	@Override
	public Map<String, Object> getDefaultSettings() {
		Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("decimal_places", "2"); // Default number of decimal places
        return settings;
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, Double value, int delta) {

	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

        TextElement<Integer> decimalPlaces = new TextElement<>("decimal_places",
                (Integer)settings.getOrDefault("decimal_places", 2));
        decimalPlaces.setLabel(translate("Decimal Places"));

        elements.add(decimalPlaces);
	}

}