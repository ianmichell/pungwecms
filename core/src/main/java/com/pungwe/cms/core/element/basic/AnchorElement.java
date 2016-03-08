package com.pungwe.cms.core.element.basic;

import com.pungwe.cms.core.annotations.ui.ThemeInfo;
import com.pungwe.cms.core.element.AbstractRenderedElement;
import com.pungwe.cms.core.element.RenderedElement;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by ian on 14/01/2016.
 */
@ThemeInfo("basic/anchor")
public class AnchorElement extends AbstractRenderedElement {

	protected RenderedElement content;

	public AnchorElement() {
	}

	public AnchorElement(String title, String href, String content) {
		setTitle(title);
		setHref(href);
		setContent(new PlainTextElement(content));
	}

	public AnchorElement(String title, String href, RenderedElement content) {
		setTitle(title);
		setHref(href);
		setContent(content);
	}

	public AnchorElement(String title, String href, String target, String content) {
		setTitle(title);
		setTarget(target);
		setHref(href);
		setContent(new PlainTextElement(content));
	}

	public AnchorElement(String title, String href, String target, RenderedElement content) {
		setTitle(title);
		setTarget(target);
		setHref(href);
		setContent(content);
	}

	public String getTitle() {
		return getAttribute("title");
	}

	public void setTitle(String title) {
		addAttribute("title", title);
	}

	public String getHref() {
		return getAttribute("href");
	}

	public void setHref(String href) {
		addAttribute("href", href);
	}

	public String getTarget() {
		return getAttribute("target");
	}

	public void setTarget(String target) {
		addAttribute("target", target);
	}

	@ModelAttribute("content")
	public RenderedElement getContent() {
		return content;
	}

	public void setContent(RenderedElement content) {
		this.content = content;
	}

	@Override
	protected Collection<String> excludedAttributes() {
		return new LinkedList<>();
	}
}
