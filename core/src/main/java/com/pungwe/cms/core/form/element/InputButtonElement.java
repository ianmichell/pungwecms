package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ian on 27/02/2016.
 */
@ThemeInfo("form/input_button")
public class InputButtonElement extends AbstractFormElement<String> {

	public enum InputButtonType {
		SUBMIT, RESET, BUTTON;
	}

	public InputButtonElement(InputButtonType type, String value) {
		addAttribute("type", type.name().toLowerCase());
		setValue(value);
	}

	public InputButtonElement(InputButtonType type) {
		addAttribute("type", type.name().toLowerCase());
		setDefaultValue(StringUtils.capitalize(type.name().toLowerCase()));
	}

	public InputButtonElement(InputButtonType type, String name, String value) {
		addAttribute("type", type.name().toLowerCase());
		setValue(value);
		setName(name);
	}
}
