package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/string")
public class StringElement extends AbstractFormElement<String> {

	public int getSize() {
		if (StringUtils.isEmpty(getAttribute("size"))) {
			setSize(60);
		}
		return new Integer(getAttribute("size"));
	}

	public void setSize(int size) {
		addAttribute("size", size + "");
	}

}
