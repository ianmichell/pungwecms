package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import com.pungwe.cms.core.element.basic.PlainTextElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.Collection;

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
		this(new PlainTextElement(label), forElement);
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

	@ModelAttribute("forElement")
	public String getFor() {
		return forElement != null ? forElement.getHtmlId() : "";
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
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("for");
	}
}
