package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 08/03/2016.
 */
@ThemeInfo("basic/header")
public class HeaderElement extends AbstractRenderedElement {

	private int level = 1;
	private RenderedElement content;

	public HeaderElement(int level) {
		this.level = level;
	}

	public HeaderElement(int level, String content) {
		this(level, new PlainTextElement(content));
	}

	public HeaderElement(int level, RenderedElement content) {
		this.level = level;
		this.content = content;
	}

	@ModelAttribute("level")
	public int getLevel() {
		return level;
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
