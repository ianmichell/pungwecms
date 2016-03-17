package com.pungwe.cms.core.form.element;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractContentElement;
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
public class LabelElement extends AbstractContentElement {

	protected AbstractFormElement<?> forElement;

	public LabelElement() {

	}

	public LabelElement(String label) {
		setContent(label);
	}

	public LabelElement(RenderedElement content) {
		setContent(content);
	}

	public LabelElement(String label, AbstractFormElement<?> forElement) {
		this(new PlainTextElement(label), forElement);
	}

	public LabelElement(RenderedElement content, AbstractFormElement<?> forElement) {
		setContent(content);
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

	@Override
	protected Collection<String> excludedAttributes() {
		return Arrays.asList("for");
	}
}
