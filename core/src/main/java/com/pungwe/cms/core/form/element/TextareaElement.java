package com.pungwe.cms.core.form.element;

/**
 * Created by ian on 09/01/2016.
 */
public class TextareaElement extends StringElement {

	protected int rows;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public String getTheme() {
		return "form_textarea_element";
	}
}
