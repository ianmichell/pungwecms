package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.element.AbstractRenderedElement;

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
