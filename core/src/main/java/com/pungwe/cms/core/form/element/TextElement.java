package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 09/01/2016.
 */
@ThemeInfo("form/string")
public class TextElement extends AbstractFormRenderedElement<String> {

	protected int size = 60;

	public TextElement() {
	}

	public TextElement(String name, String value) {
		setName(name);
		setValue(value);
	}

	@ModelAttribute("size")
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		Collection<String> excluded = new LinkedList<>(super.excludedAttributes());
		excluded.add("size");
		return excluded;
	}
}
