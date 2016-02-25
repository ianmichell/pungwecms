package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.stream.Collectors;

/**
 * Created by ian on 24/02/2016.
 */
@ThemeInfo("form/label")
public class LabelElement extends AbstractRenderedElement {

	protected RenderedElement content;
	protected AbstractFormElement<?> forElement;

	public LabelElement() {

	}

	public LabelElement(String label) {
		this.content = new PlainTextElement(label);
	}

	public LabelElement(RenderedElement content) {
		this.content = content;
	}

	public LabelElement(String label, AbstractFormElement<?> forElement) {
		this.content = new PlainTextElement(label);
		this.forElement = forElement;
	}

	public LabelElement(RenderedElement content, AbstractFormElement<?> forElement) {
		this.content = content;
		this.forElement = forElement;
	}

	public AbstractFormElement<?> getForElement() {
		return forElement;
	}

	public void setForElement(AbstractFormElement<?> forElement) {
		this.forElement = forElement;
	}

	@ModelAttribute("content")
	public RenderedElement getContent() {
		return content;
	}

	public void setContent(RenderedElement content) {
		this.content = content;
	}

	public void setContent(String content) {
		this.content = new PlainTextElement(content);
	}

	@Override
	@ModelAttribute("attributes")
	public String getAttributesAsString() {
		String attributes = "";
		if (getAttributes().containsKey("for") && getForElement() != null) {
			getAttributes().remove("for");
		}

		if (getForElement() != null) {
			return " for=\"" + getForElement().getHtmlId() + "\" " + getAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}

		if (!getAttributes().isEmpty()) {
			return " " + getAttributes().entrySet().stream().map(e -> e.getKey() + "=\"" + e.getValue() + "\"").collect(Collectors.joining(" "));
		}

		return "";
	}
}
