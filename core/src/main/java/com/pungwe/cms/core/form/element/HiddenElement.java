package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;

/**
 * Created by ian on 20/02/2016.
 */
@ThemeInfo("form/hidden")
public class HiddenElement extends AbstractFormRenderedElement<String> {

	public HiddenElement() {

	}

	public HiddenElement(String name, String value) {
		setName(name);
		setDefaultValue(value);
	}
}
