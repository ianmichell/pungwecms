package com.pungwe.cms.core.field.widgets;

import com.pungwe.cms.core.annotations.stereotypes.FieldWidget;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.entity.FieldConfig;
import com.pungwe.cms.core.field.FieldWidgetDefinition;
import com.pungwe.cms.core.form.element.EmailElement;
import com.pungwe.cms.core.form.element.TextElement;
import com.pungwe.cms.core.form.validation.EmailAddressValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pungwe.cms.core.utils.Utils.translate;

/**
 * Created by ian on 10/01/2016.
 */
@FieldWidget(value = "email_widget", label = "Email Address", supports = "email_field")
public class EmailWidget implements FieldWidgetDefinition<String> {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return new HashMap<>();
	}

	@Override
	public void buildWidgetForm(List<RenderedElement> elements, FieldConfig field, String value, int delta) {

        Map<String, Object> settings = field.getSettings();
        if (settings == null) {
            settings = getDefaultSettings();
        }
        EmailElement emailElement = new EmailElement();
        emailElement.setLabel(field.getLabel());
        emailElement.setName(field.getName());
        emailElement.setDelta(delta);
        emailElement.setDefaultValue((String)settings.get("default_value"));
        emailElement.setValue(value);

        // Set validator
        emailElement.addValidator(new EmailAddressValidator());

        elements.add(emailElement);
	}

	@Override
	public void buildWidgetSettingsForm(List<RenderedElement> elements, Map<String, Object> settings) {

        EmailElement emailElement = new EmailElement();
        emailElement.setLabel(translate("Default Value"));
        emailElement.setDefaultValue((String)settings.get("default_value"));
        emailElement.setName("default_value");

        // Set validator
        emailElement.addValidator(new EmailAddressValidator());

        elements.add(emailElement);
	}

    @Override
    public String extractValueFromForm(FieldConfig field, Map<String, Object> values, int delta) {
        return null;
    }
}