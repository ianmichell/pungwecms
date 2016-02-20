package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.element.AbstractRenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by ian on 14/01/2016.
 */
public class PlainTextElement extends AbstractRenderedElement {

	protected String text;

	public PlainTextElement() {

	}

	public PlainTextElement(String text) {
		this.text = text;
	}

	@ModelAttribute("text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getTheme() {
		return "plain_text";
	}
}
