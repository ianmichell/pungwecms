package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/textarea")
public class TextareaElement extends StringElement {

	protected int rows;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
