package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 03/03/2016.
 */
@ThemeInfo("basic/span")
public class SpanElement extends AbstractRenderedElement {

	protected RenderedElement content;

	public SpanElement() {

	}

	public SpanElement(String content) {
		this(new PlainTextElement(content));
	}

	public SpanElement(RenderedElement content) {
		setContent(content);
	}

	@ModelAttribute("content")
	public RenderedElement getContent() {
		return content;
	}

	public void setContent(RenderedElement content) {
		this.content = content;
	}

	public void setContent(String content) {
		setContent(new PlainTextElement(content));
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
