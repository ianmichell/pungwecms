package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by ian on 27/02/2016.
 */
@ThemeInfo("form/input_button")
public class InputButtonRenderedElement extends AbstractFormRenderedElement<String> {

	public enum InputButtonType {
		SUBMIT, RESET, BUTTON;
	}

	@Override
	public String getName() {
		if (StringUtils.isBlank(super.getName())) {
			setName(getAttribute("type"));
		}
		return super.getName();
	}

	@Override
	@ModelAttribute("name")
	public String getElementName() {
		return getName();
	}

	public InputButtonRenderedElement(InputButtonType type, String value) {
		addAttribute("type", type.name().toLowerCase());
		setValue(value);
	}

	public InputButtonRenderedElement(InputButtonType type) {
		addAttribute("type", type.name().toLowerCase());
		setDefaultValue(StringUtils.capitalize(type.name().toLowerCase()));
	}

	public InputButtonRenderedElement(InputButtonType type, String name, String value) {
		addAttribute("type", type.name().toLowerCase());
		setValue(value);
		setName(name);
	}
}
