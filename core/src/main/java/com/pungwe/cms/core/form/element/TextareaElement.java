package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/textarea")
public class TextareaElement extends StringElement {

	public int getRows() {
		if (!getAttributes().containsKey("rows")) {
			addAttribute("rows", "5");
		}
		return new Integer(getAttribute("rows"));
	}

	public void setRows(int rows) {
		addAttribute("rows", rows + "");
	}

}
