package com.pungwe.cms.core.form.element;

/**
 * Created by ian on 09/01/2016.
 */
public class StringElement extends AbstractFormElement<String> {

	int size = 60;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String getTheme() {
		return "form_string_element";
	}
}
