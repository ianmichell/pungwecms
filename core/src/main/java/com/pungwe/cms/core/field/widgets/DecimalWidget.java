package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.form.validation.DecimalValidator;
import com.pungwe.cms.core.form.validation.NumberValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.LocaleResolver;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 10/01/2016.
 */
@FieldWidget(value = "decimal_widget", label = "Decimal", supports = "decimal_field")
public class DecimalWidget implements FieldWidgetDefinition<Double> {

	@Autowired
	LocaleResolver localeResolver;

	@Override
	public Map<String, Object> getDefaultSettings() {
		Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("decimal_places", "2"); // Default number of decimal places
        return settings;
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, Double value, int delta) {

        Map<String, Object> widgetSettings = (Map<String, Object>)field.getSettings();
		String defaultValue = (String)widgetSettings.get("default_value");

		if (StringUtils.isNotBlank(defaultValue)) {
            BigDecimal bigDecimal = new BigDecimal(defaultValue);
            bigDecimal = bigDecimal.setScale(Integer.valueOf((String)widgetSettings.get("decimal_places")), BigDecimal.ROUND_HALF_EVEN);
            defaultValue = bigDecimal.toPlainString();
		}

		TextElement textElement = new TextElement();
		textElement.setDelta(delta);
		textElement.setName(field.getName());
		textElement.setLabel(field.getLabel());
		textElement.setDefaultValue(defaultValue);

        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value);
            bigDecimal = bigDecimal.setScale(Integer.valueOf((String)widgetSettings.get("decimal_places")), BigDecimal.ROUND_HALF_EVEN);
            textElement.setValue(bigDecimal.toString());
        }

		elements.add(textElement);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

        TextElement decimalPlaces = new TextElement("decimal_places",
				(String)settings.getOrDefault("decimal_places", "2"));
        decimalPlaces.setLabel(translate("Decimal Places"));
        decimalPlaces.setDefaultValue((String) settings.get("decimal_places"));
        decimalPlaces.addValidator(new NumberValidator());
        elements.add(decimalPlaces);

        TextElement defaultValue = new TextElement();
        defaultValue.setName("default_value");
        defaultValue.setLabel(translate("Default Value"));
        defaultValue.addValidator(new DecimalValidator());

        String value = (String)settings.get("default_value");

        if (StringUtils.isNotBlank(value)) {
            BigDecimal bigDecimal = new BigDecimal(value);
            bigDecimal = bigDecimal.setScale(Integer.valueOf((String)settings.get("decimal_places")), BigDecimal.ROUND_HALF_EVEN);
            defaultValue.setDefaultValue(bigDecimal.toPlainString());
        }
        elements.add(defaultValue);
	}

}